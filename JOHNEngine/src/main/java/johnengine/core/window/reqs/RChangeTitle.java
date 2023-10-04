package johnengine.core.window.reqs;

import org.lwjgl.glfw.GLFW;

public final class RChangeTitle extends AWindowRequest {
    private final String title;
    
    public RChangeTitle(long window, String title) {
        this.title = title;
    }


    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setTitle(title);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFW.glfwSetWindowTitle(context.window.getWindowID(), this.title);
    }
}
