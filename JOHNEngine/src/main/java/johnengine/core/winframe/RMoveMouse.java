package johnengine.core.winframe;

import org.lwjgl.glfw.GLFW;

public final class RMoveMouse extends ABasicWindowRequest {
    private final int x;
    private final int y;
    
    public RMoveMouse(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    protected void setState(BasicWindowRequestContext context) {
        //context.window.setPosition(x, y);
    }
    
    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        GLFW.glfwSetCursorPos(context.window.getWindowID(), this.x, this.y);
    }
}
