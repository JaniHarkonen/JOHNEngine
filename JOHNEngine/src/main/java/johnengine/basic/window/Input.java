package johnengine.basic.window;

import org.lwjgl.glfw.GLFW;

import johnengine.core.input.AInput;

public final class Input extends AInput {
    private final Window hostWindow;

    public Input(Window hostWindow) {
        super(new State(), new State());
        this.hostWindow = hostWindow;
    }
    
    
    @Override
    public void setup() {
        long windowID = this.hostWindow.getWindowID();
        GLFW.glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> keyListener(key, action));
        GLFW.glfwSetCursorPosCallback(windowID, (handle, xpos, ypos) -> mousePositionListener(xpos, ypos));
        GLFW.glfwSetMouseButtonCallback(windowID, (handle, button, action, mode) -> mouseListener(button, action));
        //GLFW.glfwSetScrollCallback(window, cbfun)
    }
    
    private void keyListener(int key, int action) {
        if( action == GLFW.GLFW_REPEAT )
        return;
        
        this.setStateKey(this.updatingState, key, action + 1);
    }
    
    private void mousePositionListener(double mouseX, double mouseY) {
        this.setStateMousePosition(this.updatingState, mouseX, mouseY);
    }
    
    private void mouseListener(int button, int action) {
        this.setStateMouseButton(this.updatingState, button, action + 1);
    }
}
