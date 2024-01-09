package johnengine.basic.window;

import java.awt.geom.Point2D;

import org.lwjgl.glfw.GLFW;

import johnengine.core.input.IInput;

public final class MouseKeyboardInput implements IInput {
    
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
    
    public static class KeyDown extends KeyboardEvent implements IInput.Event<Boolean> {
        
        public KeyDown(int key) {
            super(key);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = ((MouseKeyboardInput.State) state).isKeyDown(this.key);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ KeyReleased-class ************************/
    
    public static class KeyReleased extends KeyboardEvent implements IInput.Event<Boolean> {
        
        public KeyReleased(int key) {
            super(key);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = ((MouseKeyboardInput.State) state).isKeyReleased(this.key);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ MouseDown-class ************************/
    
    public static class MouseDown extends MouseEvent implements IInput.Event<Boolean> {
        
        public MouseDown(int mouseButton) {
            super(mouseButton);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = ((MouseKeyboardInput.State) state).isMouseDown(this.mouseButton);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ MouseReleased-class ************************/
    
    public static class MouseReleased extends MouseEvent implements IInput.Event<Boolean> {
        
        public MouseReleased(int mouseButton) {
            super(mouseButton);
        }

        @Override
        public boolean check(IInput.State state) {
            this.didOccur = ((MouseKeyboardInput.State) state).isMouseReleased(this.mouseButton);
            return this.didOccur;
        }
        
        @Override
        public Boolean getValue() {
            return this.didOccur;
        }
    }
    
    
    /************************ MouseMove-class ************************/
    
    public static class MouseMove implements IInput.Event<Point2D.Double> {
        protected Point2D.Double mouseDelta;
        
        public MouseMove() {
            this.mouseDelta = new Point2D.Double();
        }

        @Override
        public boolean check(IInput.State state) {
            MouseKeyboardInput.State cstate = (MouseKeyboardInput.State) state;
            this.mouseDelta.x = cstate.getMouseDeltaX();
            this.mouseDelta.y = cstate.getMouseDeltaY();
            return true;
        }
        
        @Override
        public Point2D.Double getValue() {
            return this.mouseDelta;
        }
    }
    
    
    /************************ State-class ************************/
        
        // The state has been promoted to its own class because a snapshot has 
        // to be maintained for each tick
    public class State implements IInput.State {
        private static final int KEY_MAP_SIZE = GLFW.GLFW_KEY_LAST + 1;
        private static final int MOUSE_BUTTON_MAP_SIZE = GLFW.GLFW_MOUSE_BUTTON_LAST + 1;
        
        private static final int INPUT_NO_ACTION = 0;
        private static final int INPUT_RELEASED = 1;
        private static final int INPUT_PRESSED = 2;
        
        private final int[] keyMap;
        private final int[] buttonMap;
        private double mouseX;
        private double mouseY;
        private double mouseDeltaX;
        private double mouseDeltaY;
        
        public State() {
            this.keyMap = new int[KEY_MAP_SIZE];
            this.buttonMap = new int[MOUSE_BUTTON_MAP_SIZE];
            this.mouseX = 0;
            this.mouseY = 0;
            this.mouseDeltaX = 0;
            this.mouseDeltaY = 0;
        }
        
        
        @Override
        public void takeSnapshot(IInput.State destState) {
            State dest = (State) destState;
            for( int i = 0; i < KEY_MAP_SIZE; i++ )
            {
                dest.keyMap[i] = this.keyMap[i];
                
                    // Reset released keys
                if( this.keyMap[i] == INPUT_RELEASED )
                this.keyMap[i] = INPUT_NO_ACTION;
                
                if( i >= MOUSE_BUTTON_MAP_SIZE )
                continue;
                
                dest.buttonMap[i] = this.buttonMap[i];
                
                    // Reset released mouse buttons
                if( this.buttonMap[i] == INPUT_RELEASED )
                this.buttonMap[i] = INPUT_NO_ACTION;
            }
            
            dest.mouseDeltaX = this.mouseX - dest.mouseX;
            dest.mouseDeltaY = this.mouseY - dest.mouseY;
            dest.mouseX = this.mouseX;
            dest.mouseY = this.mouseY;
        }
        
        private boolean checkKey(int key, int state) {
            return (this.keyMap[key] == state);
        }
        
        private boolean checkMouseButton(int mouseButton, int state) {
            return (this.buttonMap[mouseButton] == state);
        }
        
        public boolean isKeyDown(int key) {
            return this.checkKey(key, INPUT_PRESSED);
        }
        
        public boolean isKeyReleased(int key) {
            return this.checkKey(key, INPUT_RELEASED);
        }
        
        public boolean isMouseDown(int button) {
            return this.checkMouseButton(button, INPUT_PRESSED);
        }
        
        public boolean isMouseReleased(int button) {
            return this.checkMouseButton(button, INPUT_RELEASED);
        }
        
        public double getMouseX() {
            return this.mouseX;
        }
        
        public double getMouseY() {
            return this.mouseY;
        }
    
        public double getMouseDeltaX() {
            return this.mouseDeltaX;
        }
    
        public double getMouseDeltaY() {
            return this.mouseDeltaY;
        }
    }
    
    
    /************************ MouseKeyboardInput-class ************************/
    
    private final Window hostWindow;
    private final State updatingState;
    private final State snapshotState;

    public MouseKeyboardInput(Window hostWindow) {
        this.hostWindow = hostWindow;
        this.updatingState = new State();
        this.snapshotState = new State();
    }
    
    
    @Override
    public void setup() {
        long windowID = this.hostWindow.getWindowID();
        GLFW.glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> keyListener(key, action));
        GLFW.glfwSetCursorPosCallback(windowID, (handle, xpos, ypos) -> mousePositionListener(xpos, ypos));
        GLFW.glfwSetMouseButtonCallback(windowID, (handle, button, action, mode) -> mouseListener(button, action));
        //GLFW.glfwSetScrollCallback(window, cbfun)
    }
    
    @Override
    public void snapshot() {
        this.updatingState.takeSnapshot(this.snapshotState);
    }
    
    private void keyListener(int key, int action) {
        if( action != GLFW.GLFW_REPEAT )
        this.setStateKey(this.updatingState, key, action + 1);
    }
    
    private void mousePositionListener(double mouseX, double mouseY) {
        this.setStateMousePosition(this.updatingState, mouseX, mouseY);
    }
    
    private void mouseListener(int button, int action) {
        this.setStateMouseButton(this.updatingState, button, action + 1);
    }
    
    
    /***************************** SETTERS ****************************/
    
    protected void setStateKey(State state, int key, int value) {
        state.keyMap[key] = value;
    }
    
    protected void setStateMouseButton(State state, int mouseButton, int value) {
        state.buttonMap[mouseButton] = value;
    }
    
    protected void setStateMousePosition(State state, double mouseX, double mouseY) {
        state.mouseX = mouseX;
        state.mouseY = mouseY;
    }
    
    
    /***************************** GETTERS ****************************/
    
    @Override
    public State getState() {
        return this.snapshotState;
    }
}
