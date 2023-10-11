package johnengine.core.window.framework;

import org.lwjgl.glfw.GLFW;

public final class RResize extends ABasicWindowRequest {
    private final int width;
    private final int height;
    
    public RResize(long windowID, int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setSize(width, height);        
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        GLFW.glfwSetWindowSize(context.window.getWindowID(), this.width, this.height);
    }
}
