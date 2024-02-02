package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RMoveGL extends AWindowRequestGL {
    private final int x;
    private final int y;
    
    public RMoveGL(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setPosition(x, y);
    }
    
    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFW.glfwSetWindowPos(context.window.getWindowID(), this.x, this.y);
    }
}
