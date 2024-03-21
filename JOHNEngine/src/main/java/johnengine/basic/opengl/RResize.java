package johnengine.basic.opengl;

import java.awt.Point;

import org.lwjgl.glfw.GLFW;

import johnengine.core.window.IWindow;
import johnengine.core.window.IWindowRequest;
import johnengine.core.window.IWindow.Properties;

public class RResize implements IWindowRequest {

    public int width;
    public int height;
    public WindowGL window;
    
    public RResize(int width, int height, WindowGL window) {
        this.width = width;
        this.height = height;
        this.window = window;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        GLFW.glfwSetWindowSize(this.window.getWindowID(), this.width, this.height);
        affectedProperties.size.currentValue = new Point(this.width, this.height);
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.SIZE;
    }
}
