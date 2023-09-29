package core.window;

import org.lwjgl.glfw.GLFW;

public class Input {
    
        // The state has been promoted to its own class because a snapshot has 
        // to be maintained for each tick
    public static class State {
        private static final int KEY_MAP_SIZE = GLFW.GLFW_KEY_LAST + 1;
        private static final int MOUSE_BUTTON_MAP_SIZE = GLFW.GLFW_MOUSE_BUTTON_LAST + 1;
        
        private static final int INPUT_NO_ACTION = 0;
        private static final int INPUT_RELEASED = 1;
        private static final int INPUT_PRESSED = 2;
        
        private final int[] keyMap;
        private final int[] buttonMap;
        private double mouseX;
        private double mouseY;
        
        private State() {
            this.keyMap = new int[KEY_MAP_SIZE];
            this.buttonMap = new int[MOUSE_BUTTON_MAP_SIZE];
        }
        
        public static State createNullState() {
            return new State();
        }
        
        private void takeSnapshot(State dest) {
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
            
            dest.mouseX = this.mouseX;
            dest.mouseY = this.mouseY;
        }
        
        private boolean checkKey(int key, int state) {
            return (this.keyMap[key] == state);
        }
        
        private boolean checkMouseButton(int mouseButton, int state) {
            return (this.buttonMap[mouseButton] == state);
        }
        
        public boolean isKeyReleased(int key) {
            return this.checkKey(key, INPUT_RELEASED);
        }
        
        public boolean isKeyDown(int key) {
            return this.checkKey(key, INPUT_PRESSED);
        }
        
        public boolean isMouseReleased(int button) {
            return this.checkMouseButton(button, INPUT_RELEASED);
        }
        
        public boolean isMouseDown(int button) {
            return this.checkMouseButton(button, INPUT_PRESSED);
        }
        
        public double getMouseX() {
            return this.mouseX;
        }
        
        public double getMouseY() {
            return this.mouseY;
        }
    }
    
    
    /************************************* Input-class **************************************/
    
    private final Window hostWindow;
    private final State updatingState;
    private final State snapshotState;

    public Input(Window hostWindow) {
        this.hostWindow = hostWindow;
        this.updatingState = new State();
        this.snapshotState = new State();
    }
    
    public void attach() {
        long windowID = this.hostWindow.getWindowID();
        GLFW.glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> keyListener(key, action));
        GLFW.glfwSetCursorPosCallback(windowID, (handle, xpos, ypos) -> mousePositionListener(xpos, ypos));
        GLFW.glfwSetMouseButtonCallback(windowID, (handle, button, action, mode) -> mouseListener(button, action));
    }
    
    public void snapshot() {
        this.updatingState.takeSnapshot(this.snapshotState);
    }
    
    public State getState() {
        return this.snapshotState;
    }
    
    private void keyListener(int key, int action) {
        if( action == GLFW.GLFW_REPEAT )
        return;
        
        this.updatingState.keyMap[key] = action + 1;
    }
    
    private void mousePositionListener(double mouseX, double mouseY) {
        this.updatingState.mouseX = mouseX;
        this.updatingState.mouseY = mouseY;
    }
    
    private void mouseListener(int button, int action) {
        this.updatingState.buttonMap[button] = action + 1;
    }
}
