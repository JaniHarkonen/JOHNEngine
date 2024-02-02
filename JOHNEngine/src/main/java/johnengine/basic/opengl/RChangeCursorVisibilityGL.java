package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RChangeCursorVisibilityGL extends AWindowRequestGL {
    private final boolean isVisible;
    
    public RChangeCursorVisibilityGL(boolean isVisible) {
        this.isVisible = isVisible;
    }

    
    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setCursorVisibility(this.isVisible);
    }
    
    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFW.glfwSetInputMode(
            context.window.getWindowID(),
            GLFW.GLFW_CURSOR,
            this.isVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN
        );
    }
}
