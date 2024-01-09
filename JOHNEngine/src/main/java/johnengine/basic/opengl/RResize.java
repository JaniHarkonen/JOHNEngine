package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RResize extends AWindowRequest {
    private final int width;
    private final int height;
    
    public RResize(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setSize(width, height);        
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFW.glfwSetWindowSize(context.window.getWindowID(), this.width, this.height);
    }
}
