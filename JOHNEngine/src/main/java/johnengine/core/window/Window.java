package johnengine.core.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import johnengine.core.IEngineComponent;
import johnengine.core.reqmngr.RequestManager;
import johnengine.core.threadable.IThreadable;
import johnengine.core.window.reqs.WindowRequestContext;

public class Window extends AWindowFramework implements IEngineComponent, IThreadable {
    
    public static Window instance;
    
    private static final Input.State NULL_STATE = Input.State.createNullState();
    
    private Renderer renderer;
    private Input input;
    
    public static Window setup() {
        return new Window();
    }
    
    public Window() {
        super(new Properties(), new Properties(), new RequestManager());
        
        this.requestManager.setContext(new WindowRequestContext(this));
        instance = this;
    }
    
    
    @Override
    public void start() {
        GLFW.glfwInit();
        this.primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        //this.createWindow();
        this.windowID = this.createWindow2();
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
        
        while( /*!GLFW.glfwWindowShouldClose(this.windowID)*/this.updatingProperties.windowState != STATE.CLOSED )
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
    }

    @Override
    public void afterTick(float deltaTime) {
        
    }
    
    private long createWindow() {
        //this.primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        //GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        /*this.windowID = GLFW.glfwCreateWindow(
            Properties.DEFAULT_WIDTH,
            Properties.DEFAULT_HEIGHT,
            Properties.DEFAULT_TITLE,
            MemoryUtil.NULL,
            MemoryUtil.NULL
        );
        
        GLFW.glfwSetWindowFocusCallback(this.windowID, (window, isFocused) -> focusListener(isFocused));
        GLFW.glfwSetWindowMaximizeCallback(this.windowID, (window, isMaximized) -> maximizeListener(isMaximized));
        GLFW.glfwSetWindowPosCallback(this.windowID, (window, xpos, ypos) -> positionListener(xpos, ypos));
        GLFW.glfwSetFramebufferSizeCallback(this.windowID, (window, width, height) -> resizeListener(width, height));*/
        
        /*this.input = new Input(this);
        this.input.attach();
        */
        /*if( !Properties.DEFAULT_IS_CURSOR_VISIBLE )
        GLFW.glfwSetInputMode(this.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);*/

        /*GLFW.glfwShowWindow(this.windowID);
        this.renderer = new Renderer();
        this.setWindowState(STATE.OPEN);*/
        
        return this.windowID;
    }
    
    private long createWindow2() {
        long winID = GLFW.glfwCreateWindow(
            this.updatingProperties.width,
            this.updatingProperties.height,
            this.updatingProperties.title,
            MemoryUtil.NULL,
            MemoryUtil.NULL
        );
        
        GLFW.glfwSetWindowFocusCallback(winID, (window, isFocused) -> focusListener(isFocused));
        GLFW.glfwSetWindowMaximizeCallback(winID, (window, isMaximized) -> maximizeListener(isMaximized));
        GLFW.glfwSetWindowPosCallback(winID, (window, xpos, ypos) -> positionListener(xpos, ypos));
        GLFW.glfwSetFramebufferSizeCallback(winID, (window, width, height) -> resizeListener(width, height));
        GLFW.glfwSetWindowCloseCallback(winID, (window) -> closeListener());
        
        if( !this.updatingProperties.isCursorVisible )
        GLFW.glfwSetInputMode(winID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
        
        GLFW.glfwSetWindowPos(winID, this.updatingProperties.x, this.updatingProperties.y);
        
        return winID;
    }
    
    private void setupRenderer() {
        this.renderer = new Renderer();
        this.renderer.initialize();
    }
    
    private void rebuildWindow() {
        GLFW.glfwDestroyWindow(this.windowID);
        this.setWindowState(STATE.INITIALIZING);
        this.windowID = this.createWindow2();
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
    
    public void DEBUGgoFullscreen() {
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(this.primaryMonitorID);
        this.setSize(videoMode.width(), videoMode.height());
        this.setPosition(0, 0);
        
        this.rebuildWindow();
        //GLFW.glfwGetVideoMode(this.primaryMonitorID);
        //this.setSize(, height);
    }
}
