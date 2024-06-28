package johnengine.basic.opengl;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindow.Properties;
import johnengine.core.window.IWindowRequest;

public class RLimitFPS implements IWindowRequest {

    public long fpsMax;
    public WindowGL window;
    
    public RLimitFPS(long fpsMax) {
        this.fpsMax = fpsMax;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        affectedProperties.fpsMax.set(this.fpsMax);
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.FPS_MAX;
    }
}
