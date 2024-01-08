package johnengine.core.input;

import org.lwjgl.glfw.GLFW;

public abstract class AInput implements IInput {

        // The state has been promoted to its own class because a snapshot has 
        // to be maintained for each tick
    public static class State implements IInput.State {
        private static final int KEY_MAP_SIZE = GLFW.GLFW_KEY_LAST + 1;
        private static final int MOUSE_BUTTON_MAP_SIZE = GLFW.GLFW_MOUSE_BUTTON_LAST + 1;
        
        private static final int INPUT_NO_ACTION = 0;
        private static final int INPUT_RELEASED = 1;
        private static final int INPUT_PRESSED = 2;
        
        private final int[] keyMap;
        private final int[] buttonMap;
        private double mouseX;
        private double mouseY;
        
        public State() {
            this.keyMap = new int[KEY_MAP_SIZE];
            this.buttonMap = new int[MOUSE_BUTTON_MAP_SIZE];
            this.mouseX = 0;
            this.mouseY = 0;
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
            
            dest.mouseX = this.mouseX;
            dest.mouseY = this.mouseY;
        }
        
        private boolean checkKey(int key, int state) {
            return (this.keyMap[key] == state);
        }
        
        private boolean checkMouseButton(int mouseButton, int state) {
            return (this.buttonMap[mouseButton] == state);
        }
        
        @Override
        public boolean isKeyDown(int key) {
            return this.checkKey(key, INPUT_PRESSED);
        }
        
        @Override
        public boolean isKeyReleased(int key) {
            return this.checkKey(key, INPUT_RELEASED);
        }
        
        @Override
        public boolean isMouseDown(int button) {
            return this.checkMouseButton(button, INPUT_PRESSED);
        }
        
        @Override
        public boolean isMouseReleased(int button) {
            return this.checkMouseButton(button, INPUT_RELEASED);
        }
        
        @Override
        public double getMouseX() {
            return this.mouseX;
        }
        
        @Override
        public double getMouseY() {
            return this.mouseY;
        }
    }
    
    
    /************************* AInput-class **************************/
    
    protected final State updatingState;
    protected final State snapshotState;

    protected AInput(State updatingState, State snapshotState) {
        this.updatingState = updatingState;
        this.snapshotState = snapshotState;
    }
    
    protected AInput() {
        this(new State(), new State());
    }
    

    @Override
    public void snapshot() {
        this.updatingState.takeSnapshot(this.snapshotState);
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
