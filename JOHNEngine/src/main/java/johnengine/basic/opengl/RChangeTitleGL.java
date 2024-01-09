package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RChangeTitleGL extends AWindowRequestGL {
    private final String title;
    
    public RChangeTitleGL(String title) {
        this.title = title;
    }


    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setTitle(title);
    }

    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFW.glfwSetWindowTitle(context.window.getWindowID(), this.title);
    }
}
