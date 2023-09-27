package core.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import core.engine.IEngineComponent;

public final class Window extends Thread implements IEngineComponent {
    
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;
    public static final String DEFAULT_TITLE = "Powered by JOHNEngine v.1.0.0";
    public static final int NULL_WINDOW = 0;
    
    private long windowID;
    private int width;
    private int height;
    private String title;
    private boolean hasWindowClosed;
    
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
    
    public void launch() {
        this.hasWindowClosed = false;
        this.start();
    }
    
    @Override
    public void run() {
        GLFW.glfwInit();
        
        this.windowID = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        GLFW.glfwMakeContextCurrent(this.windowID);
        GLFW.glfwSwapInterval(0);
        GLFW.glfwShowWindow(this.windowID);
        
        while( !GLFW.glfwWindowShouldClose(this.windowID) )
        {
            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(this.windowID);
        }
        
        GLFW.glfwTerminate();
        this.hasWindowClosed = true;
    }

    public int beforeTick(float deltaTime) {
        return 0;
    }

    public int afterTick(float deltaTime) {
        return 0;
    }
    
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        
            // resize the window if it has already been created
        if( this.isWindowCreated() )
        ;
    }
    
    public void setTitle(String title) {
        this.title = title;
        
        if( this.isWindowCreated() )
        GLFW.glfwSetWindowTitle(this.windowID, this.title);
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
    
    private boolean isWindowCreated() {
        return (this.windowID != NULL_WINDOW);
    }
}
