package johnengine.core.window.framework;

import org.lwjgl.glfw.GLFW;

public final class RMove extends ABasicWindowRequest {
    private final int x;
    private final int y;
    
    public RMove(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setPosition(x, y);
    }
    
    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        GLFW.glfwSetWindowPos(context.window.getWindowID(), this.x, this.y);
    }
}
