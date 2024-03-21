package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindowRequest;
import johnengine.core.window.IWindow.Properties;

public class RLockCursor implements IWindowRequest {

    public boolean isCursorLockedToCenter;
    public WindowGL window;
    
    public RLockCursor(boolean isCursorLockedToCenter, WindowGL window) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
        this.window = window;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        GLFW.glfwSetInputMode(
            this.window.getWindowID(), 
            GLFW.GLFW_CURSOR, (
                (this.isCursorLockedToCenter) ? 
                GLFW.GLFW_CURSOR_DISABLED : 
                GLFW.GLFW_CURSOR_NORMAL
            )
        );
        
        affectedProperties.isCursorLockedToCenter.currentValue = 
            this.isCursorLockedToCenter;
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.IS_CURSOR_LOCKED_TO_CENTER;
    }
}
