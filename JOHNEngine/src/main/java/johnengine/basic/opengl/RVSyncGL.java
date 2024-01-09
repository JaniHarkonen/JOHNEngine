package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

public final class RVSyncGL extends AWindowRequestGL {
    private final boolean useVSync;
    
    public RVSyncGL(boolean useVSync) {
        this.useVSync = useVSync;
    }


    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setVSync(this.useVSync);
    }

    @Override
    protected void setGLFW(WindowRequestContextGL context) {
        GLFW.glfwSwapInterval(this.useVSync ? 1 : 0);
    }
}
