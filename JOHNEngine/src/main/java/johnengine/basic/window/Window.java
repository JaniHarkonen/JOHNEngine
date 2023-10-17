package johnengine.basic.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import johnengine.core.IEngineComponent;
import johnengine.core.input.Input;
import johnengine.core.renderer.Renderer;
import johnengine.core.reqmngr.BufferedRequestManager;
import johnengine.core.threadable.IThreadable;
import johnengine.core.winframe.AWindowFramework;
import johnengine.core.winframe.BasicWindowRequestContext;

public final class Window extends AWindowFramework implements IEngineComponent, IThreadable {
    
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
    
    private static final Input.State NULL_STATE = Input.State.createNullState();
    
    private Renderer renderer;
    private Input input;
    
    public static Window setup() {
        return new Window();
    }
    
    public Window() {
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
        this.setupRenderer();
        this.setWindowState(STATE.OPEN);
        
        this.loop();
        this.stop();
    }
    
    @Override
    public void loop() {
        long startTime = System.currentTimeMillis();
        long fpsCounter = 0;
        
        while( this.updatingProperties.windowState != STATE.CLOSED )
        {            
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
            
            fpsCounter++;
            
            long currentTime = System.currentTimeMillis();
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
        GLFW.glfwTerminate();
        this.reset();
        this.setWindowState(STATE.CLOSED);
    }

    @Override
    public void beforeTick(float deltaTime) {
        if( this.input != null )
        this.input.snapshot();
        
        this.snapshotProperties.copy(this.updatingProperties);
    }

    @Override
    public void afterTick(float deltaTime) {
        this.requestManager.newBuffer();
    }
    
    private long createWindow() {
        
            // Remove deocration (borders) when in fullscreen mode
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED,
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
        
            // Set callbacks
        GLFW.glfwSetWindowFocusCallback(winID, (window, isFocused) -> focusListener(isFocused));    // Focus
        GLFW.glfwSetWindowMaximizeCallback(winID, (window, isMaximized) -> maximizeListener(isMaximized));  // Maximize
        GLFW.glfwSetWindowPosCallback(winID, (window, xpos, ypos) -> positionListener(xpos, ypos)); // Position
        GLFW.glfwSetFramebufferSizeCallback(winID, (window, width, height) -> resizeListener(width, height));   // Size
        GLFW.glfwSetWindowCloseCallback(winID, (window) -> closeListener());    // Close
        
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
    
    private void setupRenderer() {
        this.renderer = new Renderer();
        this.renderer.initialize();
    }
    
    void rebuildWindow() {
        GLFW.glfwDestroyWindow(this.windowID);
        this.setWindowState(STATE.INITIALIZING);
        this.windowID = this.createWindow();
        this.setWindowState(STATE.OPEN);
        GLFW.glfwMakeContextCurrent(this.windowID);
    }
    
    
    public Renderer getRenderer() {
        return this.renderer;
    }
    
    public Input.State getInput() {
        if( this.input == null )
        return NULL_STATE;
        
        return this.input.getState();
    }
    
    long getPrimaryMonitorID() {
        return this.primaryMonitorID;
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
    
    public boolean isFullscreen() {
        return ((WindowProperties) this.snapshotProperties).isFullscreen;
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
