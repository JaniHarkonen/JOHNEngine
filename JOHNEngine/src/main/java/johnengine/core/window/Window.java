package johnengine.core.window;

import org.lwjgl.glfw.GLFW;
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
        this.createWindow();
        GLFW.glfwMakeContextCurrent(this.windowID);
        
        //GLFW.glfwSwapInterval(0);
        this.renderer.initialize();
        this.requestManager.processRequests();  // Handle requests before entering render loop
        this.loop();
        this.stop();
    }
    
    @Override
    public void loop() {
        long startTime = System.currentTimeMillis();
        long fpsCounter = 0;
        
        while( !GLFW.glfwWindowShouldClose(windowID) )
        {
            long currentTime = System.currentTimeMillis();
            
            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(windowID);
            this.renderer.render();
            this.requestManager.processRequests();
            
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
        GLFW.glfwTerminate();
        this.renderer = null;
        this.input = null;
        this.reset();
        this.setWindowState(STATE.CLOSED);
        //GLFW.glfwDestroyWindow(this.windowID);
    }

    public void beforeTick(float deltaTime) {
        if( this.input != null )
        this.input.snapshot();
        
        this.snapshotProperties.copy(this.updatingProperties);
    }

    public void afterTick(float deltaTime) {
        
    }
    
    private long createWindow() {
        this.primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        //GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        this.windowID = GLFW.glfwCreateWindow(
            Properties.DEFAULT_WIDTH,
            Properties.DEFAULT_HEIGHT,
            Properties.DEFAULT_TITLE,
            MemoryUtil.NULL,
            MemoryUtil.NULL
        );
        //GLFW.glfwSetWindowPos(this.windowID, 0, 0);
        this.input = new Input(this);
        this.input.attach();
        
        if( !Properties.DEFAULT_IS_CURSOR_VISIBLE )
        GLFW.glfwSetInputMode(this.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);

        GLFW.glfwShowWindow(this.windowID);
        this.renderer = new Renderer();
        this.setWindowState(STATE.OPEN);
        
        return this.windowID;
    }
    
    
    public Renderer getRenderer() {
        return this.renderer;
    }
    
    public Input.State getInput() {
        if( this.input == null )
        return NULL_STATE;
        
        return this.input.getState();
    }
}
