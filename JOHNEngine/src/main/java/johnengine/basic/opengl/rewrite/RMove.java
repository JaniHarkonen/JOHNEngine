package johnengine.basic.opengl.rewrite;

import org.lwjgl.glfw.GLFW;

public final class RMove extends AWindowRequest {
    private final int x;
    private final int y;
    
    public RMove(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setPosition(x, y);
    }
    
    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFW.glfwSetWindowPos(context.window.getWindowID(), this.x, this.y);
    }
}
