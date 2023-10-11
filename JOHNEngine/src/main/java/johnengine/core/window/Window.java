package johnengine.core.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import johnengine.core.IEngineComponent;
import johnengine.core.reqmngr.BufferedRequestManager;
import johnengine.core.threadable.IThreadable;
import johnengine.core.window.framework.AWindowFramework;
import johnengine.core.window.framework.BasicWindowRequestContext;
import johnengine.core.window.input.Input;
import johnengine.core.window.renderer.Renderer;

public class Window extends AWindowFramework implements IEngineComponent, IThreadable {
    
    public static Window instance;
    
    private static final Input.State NULL_STATE = Input.State.createNullState();
    
    private Renderer renderer;
    private Input input;
    
    
    public static Window setup() {
        return new Window();
    }
    
    
    public Window() {
        super(new Properties(), new Properties(), new BufferedRequestManager());
        
        this.requestManager.setContext(new BasicWindowRequestContext(this));
        instance = this;
    }
    
    
    @Override
    public void start() {
        GLFW.glfwInit();
        this.primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        this.windowID = this.createWindow();
        this.input = new Input(this);
        this.input.attach();
        GLFW.glfwShowWindow(this.windowID);
        this.renderer = new Renderer();
        this.setWindowState(STATE.OPEN);
        GLFW.glfwMakeContextCurrent(this.windowID);
        
        this.renderer.initialize();
        this.setupRenderer();
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
        this.renderer = null;
        this.input = null;
        this.reset();
        this.setWindowState(STATE.CLOSED);
    }

    @Override
    public void beforeTick(float deltaTime) {
        if( this.input != null )
        this.input.snapshot();
        
        this.snapshotProperties.copy(this.updatingProperties);
        //this.requestManager.requestsStart();
    }

    @Override
    public void afterTick(float deltaTime) {
        this.requestManager.newBuffer();
        //this.requestManager.requestsEnd();
    }
    
    private long createWindow() {
        
            // Remove deocration (borders) when in fullscreen mode
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED,
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
    
    /********************* HOISTED METHODS ************************/
    
    // Some inherited methods may have to be hoisted in order for them
    // to be visible inside the current package as AWindowFramework
    // and its protected methods are declared in another package.
    
    
    protected void setFullscreen(boolean isFullscreen) {
        super.setFullscreen(isFullscreen);
    }
    
    protected void setPosition(int x, int y) {
        super.setPosition(x, y);
    }
    
    protected void setSize(int width, int height) {
        super.setSize(width, height);
    }
}
