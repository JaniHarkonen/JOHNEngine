package johnengine.core.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import johnengine.core.IEngineComponent;
import johnengine.testing.DebugUtils;

public final class Window implements IEngineComponent {
    
    public static Window instance;
    
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;
    public static final String DEFAULT_TITLE = "Powered by JOHNEngine v.1.0.0";
    public static final boolean DEFAULT_IS_CURSOR_VISIBLE = true;
    public static final boolean DEFAULT_IS_FULLSCREEN = false;
    public static final boolean DEFAULT_IS_DECORATED = true;
    public static final int NULL_WINDOW = 0;
    
    public static final int MOUSE_EVENT_MOVED = 1;
    public static final int MOUSE_EVENT_BUTTON = 2;
    
    private static final Input.State NULL_STATE = Input.State.createNullState();
    
    private long windowID;
    private long primaryMonitorID;
    private int width;
    private int height;
    private String title;
    private boolean hasWindowClosed;
    private int fps;
    private Renderer renderer;
    private Input input;
    private boolean isCursorVisible;
    private boolean isFullscreen;
    private boolean isDecorated;
    
    public Window() {
        instance = this;
        this.windowID = NULL_WINDOW;
        this.primaryMonitorID = MemoryUtil.NULL;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.title = DEFAULT_TITLE;
        this.hasWindowClosed = false;
        this.fps = 0;
        this.renderer = null;
        this.input = null;
        this.isCursorVisible = DEFAULT_IS_CURSOR_VISIBLE;
        this.isFullscreen = DEFAULT_IS_FULLSCREEN;
        this.isDecorated = DEFAULT_IS_DECORATED;
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
    
    public void start() {
        GLFW.glfwInit();
        this.createWindow();
        GLFW.glfwMakeContextCurrent(windowID);
        renderer.initialize();
        GLFW.glfwSwapInterval(0);
        
        long startTime = System.currentTimeMillis();
        int fpsCounter = 0;
        while( !GLFW.glfwWindowShouldClose(windowID) )
        {
            GLFW.glfwPollEvents();
            long currentTime = System.currentTimeMillis();
            GLFW.glfwSwapBuffers(windowID);
            renderer.render();
            fpsCounter++;
            
            if( currentTime - startTime >= 1000 )
            {
                fps = fpsCounter;
                fpsCounter = 0;
                startTime = currentTime;
            }
        }
        
        this.stop();
    }
    
    public void stop() {
        GLFW.glfwMakeContextCurrent(this.windowID);
        GLFW.glfwTerminate();
        this.renderer = null;
        this.input = null;
        this.windowID = NULL_WINDOW;
        this.hasWindowClosed = true;
        //GLFW.glfwDestroyWindow(this.windowID);
    }

    public int beforeTick(float deltaTime) {
        if( this.input != null )
        this.input.snapshot();
        return 0;
    }

    public int afterTick(float deltaTime) {
        return 0;
    }
    
    private long createWindow() {
        this.primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        //GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        this.windowID = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        //GLFW.glfwSetWindowPos(this.windowID, 0, 0);
        this.input = new Input(this);
        this.input.attach();
        
        if( !this.isCursorVisible )
        GLFW.glfwSetInputMode(this.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
        
        //GLFW.glfwMakeContextCurrent(this.windowID);

        GLFW.glfwShowWindow(this.windowID);
        this.renderer = new Renderer();
        
        return this.windowID;
    }
    
        // Avaialble for all sub-components in the package
    long getWindowID() {
        return this.windowID;
    }
    
    public void resize(int width, int height) {
        this.setSize(width, height);
        
            // resize the window if it has already been created
        if( this.isWindowCreated() )
        GLFW.glfwSetWindowSize(this.windowID, this.width, this.height);
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public void setTitle(String title) {
        this.title = title;
        
        if( this.isWindowCreated() )
        GLFW.glfwSetWindowTitle(this.windowID, this.title);
    }
    
    public void setCursorVisibility(boolean isVisible) {
        this.isCursorVisible = isVisible;
        
        int hide = isVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN;
        
        if( this.isWindowCreated() )
        GLFW.glfwSetInputMode(this.windowID, GLFW.GLFW_CURSOR, hide);
    }
    
    public void setFullscreen(boolean isFullscreen) {
        
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        DebugUtils.log(this, mode.width(), mode.height());
    }
    
    public Renderer getRenderer() {
        return this.renderer;
    }
    
    public Input.State getInput() {
        if( this.input == null )
        return NULL_STATE;
        
        return this.input.getState();
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
    
    public int getFPS() {
        return this.fps;
    }
    
    public boolean isCursorVisible() {
        return this.isCursorVisible;
    }
    
    private boolean isWindowCreated() {
        return (this.windowID != NULL_WINDOW);
    }
}
