package johnengine.basic.opengl;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindow.Properties;
import johnengine.core.window.IWindowRequest;

public class RFullscreen implements IWindowRequest {

    public boolean isInFullscreen;
    public WindowGL window;
    
    public RFullscreen(boolean isInFullscreen, WindowGL window) {
        this.isInFullscreen = isInFullscreen;
        this.window = window;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        affectedProperties.isInFullscreen.currentValue = this.isInFullscreen;
        this.window.rebuildWindow();
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.IS_IN_FULLSCREEN;
    }
}
