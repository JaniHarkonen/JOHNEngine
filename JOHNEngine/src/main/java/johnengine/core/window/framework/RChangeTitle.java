package johnengine.core.window.framework;

import org.lwjgl.glfw.GLFW;

public final class RChangeTitle extends ABasicWindowRequest {
    private final String title;
    
    public RChangeTitle(String title) {
        this.title = title;
    }


    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setTitle(title);
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        GLFW.glfwSetWindowTitle(context.window.getWindowID(), this.title);
    }
}
