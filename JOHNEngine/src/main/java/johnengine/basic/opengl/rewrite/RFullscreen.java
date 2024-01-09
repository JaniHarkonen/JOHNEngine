package johnengine.basic.opengl.rewrite;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public final class RFullscreen extends AWindowRequest {
    private final boolean isFullscreen;
    
    public RFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setFullscreen(this.isFullscreen);
        context.window.setPosition(0, 0);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(context.window.getPrimaryMonitorID());
        context.window.setSize(videoMode.width(), videoMode.height());
        
        context.window.rebuildWindow();
    }
}
