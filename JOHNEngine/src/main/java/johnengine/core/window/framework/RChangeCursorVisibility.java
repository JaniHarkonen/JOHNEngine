package johnengine.core.window.framework;

import org.lwjgl.glfw.GLFW;

public final class RChangeCursorVisibility extends ABasicWindowRequest {
    private final boolean isVisible;
    
    public RChangeCursorVisibility(boolean isVisible) {
        this.isVisible = isVisible;
    }

    
    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setCursorVisibility(this.isVisible);
    }
    
    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        GLFW.glfwSetInputMode(
            context.window.getWindowID(),
            GLFW.GLFW_CURSOR,
            this.isVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN
        );
    }
}
