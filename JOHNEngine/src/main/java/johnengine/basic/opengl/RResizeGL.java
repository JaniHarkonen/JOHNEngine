package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RResizeGL extends AWindowRequestGL {
    private final int width;
    private final int height;
    
    public RResizeGL(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setSize(width, height);        
    }

    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFW.glfwSetWindowSize(context.window.getWindowID(), this.width, this.height);
    }
}
