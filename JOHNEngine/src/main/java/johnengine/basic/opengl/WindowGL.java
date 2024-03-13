package johnengine.basic.opengl;

import java.awt.Point;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.opengl.input.MouseKeyboardInputGL;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.core.IEngineComponent;
import johnengine.core.renderer.IRenderer;
import johnengine.core.threadable.IThreadable;
import johnengine.core.window.IWindow;
import johnengine.core.window.WindowRequestManager;
import johnengine.testing.DebugUtils;

public final class WindowGL implements IWindow, IEngineComponent, IThreadable
{
    
    private MouseKeyboardInputGL input;
    private WindowRequestManager requestManager;
    private IWindow.Properties properties;
    private RendererGL renderer;
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
        this.reset();
        this.input = new MouseKeyboardInputGL(this);
        this.requestManager = new WindowRequestManager();
        this.properties = new IWindow.Properties();
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
        GL.createCapabilities();
        this.renderer.initialize();
        this.properties.windowState.set(IWindow.STATE_OPEN);
        
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
            this.requestManager.processRequests(this.properties);
            GLFW.glfwPollEvents();
            GLFW.glfwSwapBuffers(this.windowID);
            this.renderer.render();
            
                // Lock cursor to the center of the screen if enabled
            long inputTimestamp = this.input.getState().getTimestamp();
            if( this.isCursorLockedToCenter() && previousInputTimestamp != inputTimestamp )
            {
                Point size = this.properties.size.currentValue;
                GLFW.glfwSetCursorPos(this.windowID, size.x / 2, size.y / 2);
            }
            
                // FPS-counter
            long currentTime = System.currentTimeMillis();
            fpsCounter++;
            
            if( currentTime - startTime >= 1000 )
            {
                this.properties.fps.set(fpsCounter);
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
        
        this.properties.windowState.set(IWindow.STATE_CLOSED);
    }
    
    public void dispose() {
        GLFW.glfwTerminate();
        this.reset();
    }

    @Override
    public void beforeTick(float deltaTime) {
        this.input.snapshot();
        this.properties.snapshot();
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
            this.properties.isInFullscreen.currentValue ? 
            GLFW.GLFW_FALSE : 
            GLFW.GLFW_TRUE
        );
        
            // Create window
        Point size = this.properties.size.currentValue;
        long winID = GLFW.glfwCreateWindow(
            size.x,
            size.y,
            this.properties.title.currentValue,
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
        GLFW.glfwSetInputMode(
            winID, 
            GLFW.GLFW_CURSOR,
            this.properties.isCursorVisible.currentValue ?
            GLFW.GLFW_CURSOR_NORMAL : 
            GLFW.GLFW_CURSOR_HIDDEN
        );
        
            // Position the window
        Point wpos = this.properties.position.currentValue;
        GLFW.glfwSetWindowPos(winID, wpos.x, wpos.y);
        
        GLFW.glfwSwapInterval(this.properties.useVSync.currentValue ? 1 : 0);
        
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
        
        this.properties.windowState.set(IWindow.STATE_INITIALIZING);
        this.windowID = this.createWindow();
        this.properties.windowState.set(IWindow.STATE_OPEN);
        
        GLFW.glfwMakeContextCurrent(this.windowID);
    }
    
    
    /************************* LISTENERS ***************************/
    
    protected void focusListener(boolean isFocused) {
        this.properties.isFocused.set(isFocused);
    }
    
    protected void maximizeListener(boolean isMaximized) {
        this.properties.isMaximized.set(isMaximized);
    }
    
    protected void positionListener(int xpos, int ypos) {
        this.properties.position.set(new Point(xpos, ypos));
    }
    
    protected void resizeListener(int width, int height) {
        this.properties.size.set(new Point(width, height));
    }
    
    protected void closeListener() {
        this.properties.windowState.set(IWindow.STATE_CLOSED);
    }
    
    
    /*************************** REQUESTS ***************************/

    @Override
    public IWindow move(int x, int y) {
        this.requestManager.addRequest(new RMove(x, y, this));
        return this;
    }

    @Override
    public IWindow resize(int width, int height) {
        this.requestManager.addRequest(new RResize(width, height, this));
        return this;
    }

    @Override
    public IWindow changeTitle(String title) {
        this.requestManager.addRequest(new RChangeTitle(title, this));
        return this;
    }   

    @Override
    public IWindow showBorder() {
        //this.requestManager.addRequest(new RMove(x, y));
        return this;
    }

    @Override
    public IWindow hideBorder() {
        //this.requestManager.addRequest(new RMove(x, y));
        return this;
    }

    @Override
    public IWindow lockCursorToCenter() {
        this.requestManager.addRequest(new RLockCursor(true));
        return this;
    }

    @Override
    public IWindow releaseCursor() {
        this.requestManager.addRequest(new RLockCursor(false));
        return this;
    }

    @Override
    public IWindow showCursor() {
        this.requestManager.addRequest(new RChangeCursorVisibility(true, this));
        return this;
    }

    @Override
    public IWindow hideCursor() {
        this.requestManager.addRequest(new RChangeCursorVisibility(false, this));
        return this;
    }

    @Override
    public IWindow enableVSync() {
        this.requestManager.addRequest(new RUseVSync(true));
        return this;
    }

    @Override
    public IWindow disableVSync() {
        this.requestManager.addRequest(new RUseVSync(false));
        return this;
    }

    @Override
    public IWindow enterFullscreen() {
        this.requestManager.addRequest(new RFullscreen(true, this));
        return this;
    }

    @Override
    public IWindow exitFullscreen() {
        this.requestManager.addRequest(new RFullscreen(false, this));
        return this;
    }

    
    /*************************** GETTERS ***************************/
    
    @Override
    public int getX() {
        return this.properties.position.lastValue.x;
    }

    @Override
    public int getY() {
        return this.properties.position.lastValue.y;
    }

    @Override
    public int getWidth() {
        return this.properties.size.lastValue.x;
    }

    @Override
    public int getHeight() {
        return this.properties.size.lastValue.y;
    }

    @Override
    public String getTitle() {
        return this.properties.title.lastValue;
    }
    
    @Override
    public long getFPS() {
        return this.properties.fps.lastValue;
    }

    @Override
    public boolean hasBorder() {
        return this.properties.hasBorder.lastValue;
    }

    @Override
    public boolean isCursorLockedToCenter() {
        return this.properties.isCursorLockedToCenter.lastValue;
    }

    @Override
    public boolean isCursorVisible() {
        return this.properties.isCursorVisible.lastValue;
    }

    @Override
    public boolean useVSync() {
        return this.properties.useVSync.lastValue;
    }

    @Override
    public boolean isFocused() {
        return this.properties.isFocused.lastValue;
    }

    @Override
    public boolean isMaximized() {
        return this.properties.isMaximized.lastValue;
    }

    @Override
    public boolean isInFullscreen() {
        return this.properties.isInFullscreen.lastValue;
    }

    @Override
    public int getWindowState() {
        return this.properties.windowState.lastValue;
    }

    @Override
    public Object getProperty(String propertyKey) {
        return this.properties.getProperty(propertyKey);
    }

    @Override
    public boolean hasWindowClosed() {
        return (this.getWindowState() == STATE_CLOSED);
    }

    @Override
    public boolean isWindowClosing() {
        return (this.properties.windowState.currentValue == IWindow.STATE_CLOSED);
    }

    @Override
    public boolean isWindowInitializing() {
        return (this.getWindowState() == STATE_INITIALIZING);
    }

    @Override
    public boolean isWindowOpen() {
        return (this.getWindowState() == STATE_OPEN);
    }

    @Override
    public MouseKeyboardInputGL getInput() {
        return this.input;
    }
    
    @Override
    public RendererGL getRenderer() {
        return (RendererGL) this.renderer;
    }
    
    public long getPrimaryMonitorID() {
        return this.primaryMonitorID;
    }
    
    public long getWindowID() {
        return this.windowID;
    }

    
    /*************************** SETTERS ***************************/
    
    @Override
    public void setRenderer(IRenderer renderer) {
        this.renderer = (RendererGL) renderer;
    }
}
