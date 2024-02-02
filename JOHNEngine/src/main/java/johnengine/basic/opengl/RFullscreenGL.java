package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public final class RFullscreenGL extends AWindowRequestGL {
    private final boolean isFullscreen;
    
    public RFullscreenGL(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setFullscreen(this.isFullscreen);
        context.window.setPosition(0, 0);
    }

    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(context.window.getPrimaryMonitorID());
        context.window.setSize(videoMode.width(), videoMode.height());
        
        context.window.rebuildWindow();
    }
}
