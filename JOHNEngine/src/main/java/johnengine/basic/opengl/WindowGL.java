package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.opengl.input.MouseKeyboardInputGL;
import johnengine.basic.renderer.RendererGL;
import johnengine.core.IEngineComponent;
import johnengine.core.renderer.IRenderer;
import johnengine.core.reqmngr.BufferedRequestManager;
import johnengine.core.threadable.IThreadable;
import johnengine.core.winframe.AWindowFramework;

public final class WindowGL extends AWindowFramework 
    implements IEngineComponent, IThreadable {
    
    private MouseKeyboardInputGL input;
    private long primaryMonitorID;
    private long windowID;
    
    public static WindowGL setup3D() {
        WindowGL instance = new WindowGL();
        instance.setRenderer(new RendererGL(instance));
        return instance;
    }
    
        // TO BE IMPLEMENTED
    /*public static Window setup2D() {
        return null;
    }*/
    
    public WindowGL() {
        super(new Properties(), new Properties(), new BufferedRequestManager());
        
        this.reset();
        this.requestManager.setContext(new WindowRequestContextGL(this));
        this.input = new MouseKeyboardInputGL(this);
        this.primaryMonitorID = 0;
        this.windowID = 0;
    }
    
    
    @Override
    public void start() {
        GLFW.glfwInit();
        
        this.primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        this.windowID = this.createWindow();
        GLFW.glfwShowWindow(this.windowID);
        
        this.input.setup();
        
        GLFW.glfwMakeContextCurrent(this.windowID);
        this.renderer.initialize();
        this.setWindowState(STATE_OPEN);
        
        this.loop();
        this.stop();
    }
    
    @Override
    public void loop() {
        long startTime = System.currentTimeMillis();
        long fpsCounter = 0;
        long previousInputTimestamp = 0;
        
        while( !this.isWindowClosing() )
        {
            this.requestManager.processRequests();
            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(this.windowID);
            this.renderer.render();
            
                // Lock cursor to the center of the screen if enabled
            long inputTimestamp = this.input.getState().getTimestamp();
            if( this.isCursorLockedToCenter() && previousInputTimestamp != inputTimestamp )
            {
                Properties updating = this.updatingProperties;
                GLFW.glfwSetCursorPos(
                    this.windowID,
                    /*updating.x + */updating.width / 2,
                    /*updating.y +*/ updating.height / 2
                );
            }
            
                // FPS-counter
            long currentTime = System.currentTimeMillis();
            fpsCounter++;
            
            if( currentTime - startTime >= 1000 )
            {
                this.setFPS(fpsCounter);
                fpsCounter = 0;
                startTime = currentTime;
            }
        }
        
        this.dispose();
    }
    
    @Override
    public void stop() {
        if( this.isWindowClosing() )
        return;
        
        this.setWindowState(STATE_CLOSED);
    }

    public void dispose() {
        GLFW.glfwTerminate();
        this.reset();
    }
    
    @Override
    public void beforeTick(float deltaTime) {
        this.input.snapshot();
        this.snapshotProperties.copy(this.updatingProperties);
    }

    @Override
    public void afterTick(float deltaTime) {
        this.requestManager.newBuffer();
        this.renderer.generateRenderBuffer();
    }
    
    protected long createWindow() {
        
            // Remove deocration (borders) when in fullscreen mode
        GLFW.glfwWindowHint(
            GLFW.GLFW_DECORATED,
            this.updatingProperties.isFullscreen ? 
            GLFW.GLFW_FALSE : 
            GLFW.GLFW_TRUE
        );
        
            // Create window
        long winID = GLFW.glfwCreateWindow(
            this.updatingProperties.width,
            this.updatingProperties.height,
            this.updatingProperties.title,
            MemoryUtil.NULL,
            MemoryUtil.NULL
        );
        
            // Setup focus listener
        GLFW.glfwSetWindowFocusCallback(
            winID, 
            (window, isFocused) -> focusListener(isFocused)
        );
        
            // Setup maximization listener
        GLFW.glfwSetWindowMaximizeCallback(
            winID, 
            (window, isMaximized) -> maximizeListener(isMaximized)
        );
        
            // Setup window position listener
        GLFW.glfwSetWindowPosCallback(
            winID, 
            (window, xpos, ypos) -> positionListener(xpos, ypos)
        );
        
            // Setup resize listener
        GLFW.glfwSetFramebufferSizeCallback(
            winID, 
            (window, width, height) -> resizeListener(width, height)
        );
        
            // Setup close listener
        GLFW.glfwSetWindowCloseCallback(
            winID, 
            (window) -> closeListener()
        );
        
            // Whether cursor is to be shown or hidden
        GLFW.glfwSetInputMode(winID, GLFW.GLFW_CURSOR,
            this.updatingProperties.isCursorVisible ? 
            GLFW.GLFW_CURSOR_NORMAL : 
            GLFW.GLFW_CURSOR_HIDDEN
        );
        
            // Position the window
        GLFW.glfwSetWindowPos(winID, this.updatingProperties.x, this.updatingProperties.y);
        
        return winID;
    }
    
    protected void reset() {
        this.windowID = 0;
        this.primaryMonitorID = 0;
        this.renderer = null;
        this.input = null;
    }
    
    void rebuildWindow() {
        GLFW.glfwDestroyWindow(this.windowID);
        this.setWindowState(STATE_INITIALIZING);
        this.windowID = this.createWindow();
        this.setWindowState(STATE_OPEN);
        GLFW.glfwMakeContextCurrent(this.windowID);
    }
    
    
    /************************* REQUESTS ***************************/
    
    @Override
    public AWindowFramework move(int x, int y) {
        this.requestManager.request(new RMoveGL(x, y));
        return this;
    }

    @Override
    public AWindowFramework resize(int width, int height) {
        this.requestManager.request(new RResizeGL(width, height));
        return this;
    }

    @Override
    public AWindowFramework changeTitle(String title) {
        this.requestManager.request(new RChangeTitleGL(title));
        return this;
    }

    @Override
    public AWindowFramework lockCursorToCenter() {
        this.requestManager.request(new RLockCursorGL(true));
        return this;
    }

    @Override
    public AWindowFramework freeCursorLock() {
        this.requestManager.request(new RLockCursorGL(false));
        return this;
    }

    @Override
    public AWindowFramework showCursor() {
        this.requestManager.request(new RChangeCursorVisibilityGL(true));
        return this;
    }

    @Override
    public AWindowFramework hideCursor() {
        this.requestManager.request(new RChangeCursorVisibilityGL(false));
        return this;
    }

    @Override
    public AWindowFramework enableVSync() {
        this.requestManager.request(new RVSyncGL(true));
        return this;
    }

    @Override
    public AWindowFramework disableVSync() {
        this.requestManager.request(new RVSyncGL(false));
        return this;
    }

    @Override
    public AWindowFramework moveMouse(int x, int y) {
        this.requestManager.request(new RMoveMouseGL(x, y));
        return this;
    }
    
    @Override
    public WindowGL enterFullscreen() {
        this.requestManager.request(new RFullscreenGL(true));
        return this;
    }
    
    @Override
    public WindowGL exitFullscreen() {
        this.requestManager.request(new RFullscreenGL(false));
        return this;
    }
    
    
    /*********************** GETTERS ****************************/
    
    public long getPrimaryMonitorID() {
        return this.primaryMonitorID;
    }
    
    public long getWindowID() {
        return this.windowID;
    }
    
    public boolean isFullscreen() {
        return this.snapshotProperties.isFullscreen;
    }
    
    public MouseKeyboardInputGL getInput() {
        return this.input;
    }
    
    public IRenderer getRenderer() {
        return this.renderer;
    }
}
