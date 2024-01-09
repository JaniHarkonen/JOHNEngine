package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RMoveMouse extends AWindowRequest {
    private final int x;
    private final int y;
    
    public RMoveMouse(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    protected void setState(WindowRequestContext context) { }
    
    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFW.glfwSetCursorPos(context.window.getWindowID(), this.x, this.y);
    }
}
