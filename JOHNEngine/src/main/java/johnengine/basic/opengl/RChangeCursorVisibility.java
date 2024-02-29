package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindowRequest;
import johnengine.core.window.IWindow.Properties;

public class RChangeCursorVisibility implements IWindowRequest {

    public boolean isCursorVisible;
    public WindowGL window;
    
    public RChangeCursorVisibility(boolean isCursorVisible, WindowGL window) {
        this.isCursorVisible = isCursorVisible;
        this.window = window;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        GLFW.glfwSetInputMode(
            this.window.getWindowID(),
            GLFW.GLFW_CURSOR,
            this.isCursorVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN
        );
        
        affectedProperties.isCursorVisible.currentValue = this.isCursorVisible;
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.IS_CURSOR_VISIBLE;
    }
}
