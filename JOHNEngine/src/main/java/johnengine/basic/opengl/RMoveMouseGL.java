package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RMoveMouseGL extends AWindowRequestGL {
    private final int x;
    private final int y;
    
    public RMoveMouseGL(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    protected void setState(WindowRequestContextGL context) { }
    
    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFW.glfwSetCursorPos(context.window.getWindowID(), this.x, this.y);
    }
}
