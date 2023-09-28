package core.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import core.engine.IEngineComponent;
import testing.DebugUtils;

public final class Window extends Thread implements IEngineComponent {
    
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;
    public static final String DEFAULT_TITLE = "Powered by JOHNEngine v.1.0.0";
    public static final int NULL_WINDOW = 0;
    
    public static final int MOUSE_EVENT_MOVED = 1;
    public static final int MOUSE_EVENT_BUTTON = 2;
    
    private long windowID;
    private int width;
    private int height;
    private String title;
    private volatile boolean hasWindowClosed;
    private Renderer renderer;
    private Input input;
    
    public Window() {
        this.windowID = NULL_WINDOW;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.title = DEFAULT_TITLE;
        this.hasWindowClosed = true;
    }

    public static Window setup() {
        return new Window();
    }
    
    public void enable() {
        if( this.isWindowCreated() )
        return;

        this.hasWindowClosed = false;
        this.start();
    }
    
    @Override
    public void run() {
        GLFW.glfwInit();
        
        this.createWindow();
        
        long time = System.currentTimeMillis();
        long counter = 0;
        while( !GLFW.glfwWindowShouldClose(this.windowID) )
        {
            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(this.windowID);
            this.renderer.render();
            
            counter++;
            if( System.currentTimeMillis() - time >= 1000 )
            {
                time = System.currentTimeMillis();
                //DebugUtils.log(this, counter);
                counter = 0;
            }
        }
        
        GLFW.glfwTerminate();
        this.renderer = null;
        this.windowID = NULL_WINDOW;
        this.hasWindowClosed = true;
    }

    public int beforeTick(float deltaTime) {
        if( this.input != null )
        this.input.snapshot();
        return 0;
    }

    public int afterTick(float deltaTime) {
        return 0;
    }
    
        // Avaialble for all sub-components in the package
    long getWindowID() {
        return this.windowID;
    }
    
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        
            // resize the window if it has already been created
        if( this.isWindowCreated() )
        GLFW.glfwSetWindowSize(this.windowID, this.width, this.height);
    }
    
    public void setTitle(String title) {
        this.title = title;
        
        if( this.isWindowCreated() )
        GLFW.glfwSetWindowTitle(this.windowID, this.title);
    }
    
    public Renderer getRenderer() {
        return this.renderer;
    }
    
    public Input getInput() {
        return this.input;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public boolean hasWindowClosed() {
        return this.hasWindowClosed;
    }
    
    private long createWindow() {
        this.windowID = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        this.input = new Input(this);
        this.input.attach();
        GLFW.glfwMakeContextCurrent(this.windowID);
        GLFW.glfwSwapInterval(0);
        GLFW.glfwShowWindow(this.windowID);
        
        this.renderer = new Renderer();
        
        return this.windowID;
    }
    
    
    
    private boolean isWindowCreated() {
        return (this.windowID != NULL_WINDOW);
    }
}
