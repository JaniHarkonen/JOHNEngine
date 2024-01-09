package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RVSync extends AWindowRequest {
    private final boolean useVSync;
    
    public RVSync(boolean useVSync) {
        this.useVSync = useVSync;
    }


    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setVSync(this.useVSync);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        GLFW.glfwSwapInterval(this.useVSync ? 1 : 0);
    }
}
