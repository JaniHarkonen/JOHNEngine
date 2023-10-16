package johnengine.core.window.framework;

import org.lwjgl.glfw.GLFW;

public final class RVSync extends ABasicWindowRequest {
    private final boolean useVSync;
    
    public RVSync(boolean useVSync) {
        this.useVSync = useVSync;
    }


    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setVSync(this.useVSync);
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        GLFW.glfwSwapInterval(this.useVSync ? 1 : 0);
    }
}
