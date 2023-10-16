package johnengine.basic.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import johnengine.core.window.framework.ABasicWindowRequest;
import johnengine.core.window.framework.BasicWindowRequestContext;

public final class RFullscreen extends ABasicWindowRequest {
    private final boolean isFullscreen;
    
    public RFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    @Override
    protected void setState(BasicWindowRequestContext context) {
        Window window = (Window) context.window;
        window.setFullscreen(this.isFullscreen);
        window.setPosition(0, 0);
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        Window window = (Window) context.window;
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(window.getPrimaryMonitorID());
        window.setSize(videoMode.width(), videoMode.height());
        
        window.rebuildWindow();
    }
}
