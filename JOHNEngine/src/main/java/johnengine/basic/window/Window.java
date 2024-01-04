package johnengine.basic.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.renderer.RendererGL;
import johnengine.core.IEngineComponent;
import johnengine.core.renderer.IRenderer;
import johnengine.core.reqmngr.BufferedRequestManager;
import johnengine.core.threadable.IThreadable;
import johnengine.core.winframe.AWindowFramework;
import johnengine.core.winframe.BasicWindowRequestContext;

public final class Window extends AWindowFramework 
    implements IEngineComponent, IThreadable {
    
    public static class WindowProperties extends Properties {
        public static final boolean DEFAULT_IS_FULLSCREEN = false;
        
        public boolean isFullscreen;
        
        public WindowProperties() {
            super();
            this.isFullscreen = DEFAULT_IS_FULLSCREEN;
        }
        
        
        public void copy(WindowProperties source) {
            super.copy(source);
            this.isFullscreen = source.isFullscreen;
        }
    }
    
    
    /*************************** Window-class ****************************/
    
    protected Input input;
    
    public static Window setup3D() {
        Window instance = new Window();
        instance.setRenderer(new RendererGL(instance));
        return instance;
    }
    
        // TO BE IMPLEMENTED
    /*public static Window setup2D() {
        return null;
    }*/
    
    protected Window() {
        super(new WindowProperties(), new WindowProperties(), new BufferedRequestManager());
        
        this.requestManager.setContext(new BasicWindowRequestContext(this));
        this.input = new Input(this);
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
        
        while( true )
        {
            if( this.isWindowClosing() )
            break;
            
            this.requestManager.processRequests();
            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(this.windowID);
            this.renderer.render();
            
                // Lock cursor to the center of the screen if enabled
            if( this.isCursorLockedToCenter() )
            {
                Properties updating = this.updatingProperties;
                GLFW.glfwSetCursorPos(
                    this.windowID,
                    updating.x + updating.width / 2,
                    updating.y + updating.height / 2
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
    }
    
    @Override
    public void stop() {
        if( this.isWindowClosing() )
        return;
        
        GLFW.glfwTerminate();
        this.reset();
        this.setWindowState(STATE_CLOSED);
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
            ((WindowProperties) this.updatingProperties).isFullscreen ? 
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
    
    @Override
    protected void reset() {
        super.reset();
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
    
    public Window enterFullscreen() {
        this.requestManager.request(new RFullscreen(true));
        return this;
    }
    
    public Window exitFullscreen() {
        this.requestManager.request(new RFullscreen(false));
        return this;
    }
    
    
    /*********************** GETTERS ****************************/
    
    void setFullscreen(boolean isFullscreen) {
        ((WindowProperties) this.updatingProperties).isFullscreen = isFullscreen;
    }
    
    long getPrimaryMonitorID() {
        return this.primaryMonitorID;
    }
    
    public boolean isFullscreen() {
        return ((WindowProperties) this.snapshotProperties).isFullscreen;
    }
    
    public Input getInput() {
        return this.input;
    }
    
    public IRenderer getRenderer() {
        return this.renderer;
    }
    
    
    /********************* HOISTED METHODS ************************/
    
    // Some inherited methods may have to be hoisted in order for them
    // to be visible inside the current package as AWindowFramework
    // and its protected methods are declared in another package.
    
    protected void setPosition(int x, int y) {
        super.setPosition(x, y);
    }
    
    protected void setSize(int width, int height) {
        super.setSize(width, height);
    }
}
