package johnengine.basic.opengl;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindowRequest;
import johnengine.core.window.IWindow.Properties;

public class RLockCursor implements IWindowRequest {

    public boolean isCursorLockedToCenter;
    
    public RLockCursor(boolean isCursorLockedToCenter) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        affectedProperties.isCursorLockedToCenter.currentValue = this.isCursorLockedToCenter;
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.IS_CURSOR_LOCKED_TO_CENTER;
    }
}
