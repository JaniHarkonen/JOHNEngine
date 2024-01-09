package johnengine.basic.opengl.rewrite;

import org.lwjgl.glfw.GLFW;

public final class RChangeCursorVisibility extends AWindowRequest {
    private final boolean isVisible;
    
    public RChangeCursorVisibility(boolean isVisible) {
        this.isVisible = isVisible;
    }

    
    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setCursorVisibility(this.isVisible);
    }
    
    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFW.glfwSetInputMode(
            context.window.getWindowID(),
            GLFW.GLFW_CURSOR,
            this.isVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN
        );
    }
}
