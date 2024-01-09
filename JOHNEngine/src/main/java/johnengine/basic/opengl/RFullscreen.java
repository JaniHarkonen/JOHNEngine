package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import johnengine.core.winframe.ABasicWindowRequest;
import johnengine.core.winframe.BasicWindowRequestContext;

public final class RFullscreen extends ABasicWindowRequest {
    private final boolean isFullscreen;
    
    public RFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    @Override
    protected void setState(BasicWindowRequestContext context) {
        WindowGL window = (WindowGL) context.window;
        window.setFullscreen(this.isFullscreen);
        window.setPosition(0, 0);
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        WindowGL window = (WindowGL) context.window;
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(window.getPrimaryMonitorID());
        window.setSize(videoMode.width(), videoMode.height());
        
        window.rebuildWindow();
    }
}
