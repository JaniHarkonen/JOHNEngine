package johnengine.basic.opengl;

import johnengine.core.winframe.IWindow;
import johnengine.core.winframe.IWindowRequest;
import johnengine.core.winframe.IWindow.Properties;

public class RFullscreen implements IWindowRequest {

    public boolean isInFullscreen;
    
    public RFullscreen(boolean isInFullscreen) {
        this.isInFullscreen = isInFullscreen;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        affectedProperties.isInFullscreen.currentValue = this.isInFullscreen;
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.IS_IN_FULLSCREEN;
    }
}
