package johnengine.basic.opengl;

import org.lwjgl.glfw.GLFW;

import johnengine.core.winframe.IWindow;
import johnengine.core.winframe.IWindowRequest;
import johnengine.core.winframe.IWindow.Properties;

public class RChangeTitle implements IWindowRequest {

    public String title;
    public WindowGL window;
    
    public RChangeTitle(String title, WindowGL window) {
        this.title = title;
        this.window = window;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        GLFW.glfwSetWindowTitle(this.window.getWindowID(), this.title);
        affectedProperties.title.currentValue = this.title;
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.TITLE;
    }
}
