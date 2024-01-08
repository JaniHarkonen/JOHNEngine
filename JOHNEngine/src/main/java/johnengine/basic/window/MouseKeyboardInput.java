package johnengine.basic.window;

import java.awt.geom.Point2D;

import org.lwjgl.glfw.GLFW;

import johnengine.core.input.AInput;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputEvent;

public final class MouseKeyboardInput extends AInput {
    
    /************************ BooleanEvent-class ************************/
    
    private static class BooleanEvent {
        protected boolean didOccur;
        
        public BooleanEvent() {
            this.didOccur = false;
        }
    }
    
    
    /************************ MouseEvent-class ************************/
    
    private static class MouseEvent extends BooleanEvent {
        protected int mouseButton;
        
        public MouseEvent(int mouseButton) {
            super();
            this.mouseButton = mouseButton;
        }
    }
    
    
    /************************ KeyboardEvent-class ************************/
    
    private static class KeyboardEvent extends BooleanEvent {
        protected int key;
        
        public KeyboardEvent(int key) {
            super();
            this.key = key;
        }
    }
    
    
    /************************ KeyDown-class ************************/
    
    public static class KeyDown extends KeyboardEvent implements IInputEvent<Boolean> {
        
        public KeyDown(int key) {
            super(key);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = state.isKeyDown(this.key);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ KeyReleased-class ************************/
    
    public static class KeyReleased extends KeyboardEvent implements IInputEvent<Boolean> {
        
        public KeyReleased(int key) {
            super(key);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = state.isKeyReleased(this.key);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ MouseDown-class ************************/
    
    public static class MouseDown extends MouseEvent implements IInputEvent<Boolean> {
        
        public MouseDown(int mouseButton) {
            super(mouseButton);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = state.isMouseDown(this.mouseButton);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ MouseReleased-class ************************/
    
    public static class MouseReleased extends MouseEvent implements IInputEvent<Boolean> {
        
        public MouseReleased(int mouseButton) {
            super(mouseButton);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = state.isMouseReleased(this.mouseButton);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    public static class MouseMove implements IInputEvent<Point2D.Double> {
        protected Point2D.Double mouse;
        
        public MouseMove() {
            this.mouse = new Point2D.Double();
        }

        @Override
        public boolean check(IInput.State state) {
            this.mouse.x = state.getMouseX();
            this.mouse.y = state.getMouseY();
            return true;
        }
        
        @Override
        public Point2D.Double getValue() {
            return this.mouse;
        }
    }
    
    
    /************************ MouseKeyboardInput-class ************************/
    
    private final Window hostWindow;

    public MouseKeyboardInput(Window hostWindow) {
        super(new AInput.State(), new AInput.State());
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
