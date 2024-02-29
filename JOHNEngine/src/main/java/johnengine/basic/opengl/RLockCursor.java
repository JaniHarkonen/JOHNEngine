package johnengine.basic.opengl;

import johnengine.core.winframe.IWindow;
import johnengine.core.winframe.IWindowRequest;
import johnengine.core.winframe.IWindow.Properties;

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
