package johnengine.basic.opengl;

import java.awt.Point;

import org.lwjgl.glfw.GLFW;

import johnengine.core.winframe.IWindow;
import johnengine.core.winframe.IWindowRequest;
import johnengine.core.winframe.IWindow.Properties;

public class RMove implements IWindowRequest {

    public int x;
    public int y;
    public WindowGL window;
    
    public RMove(int x, int y, WindowGL window) {
        this.x = x;
        this.y = y;
        this.window = window;
    }
    
    
    @Override
    public void fulfill(Properties affectedProperties) {
        GLFW.glfwSetWindowPos(this.window.getWindowID(), this.x, this.y);
        affectedProperties.position.currentValue = new Point(this.x, this.y);
    }

    @Override
    public String getPropertyKey() {
        return IWindow.Properties.POSITION;
    }
}
