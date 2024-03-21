package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindowRequest;
import johnengine.core.window.IWindow.Properties;

public class RUseVSync implements IWindowRequest {

    public boolean useVSync;
    
    public RUseVSync(boolean useVSync) {
        this.useVSync = useVSync;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        GLFW.glfwSwapInterval(this.useVSync ? 1 : 0);
        affectedProperties.useVSync.currentValue = this.useVSync;
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.USE_V_SYNC;
    }
}
