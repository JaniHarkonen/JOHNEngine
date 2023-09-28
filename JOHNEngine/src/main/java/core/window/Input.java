package core.window;

import org.lwjgl.glfw.GLFW;

import testing.DebugUtils;

public class Input {
    
    /*public class MouseInfo {
        private double x;
        private double y;
        private final int[] buttonMap;
        
        private MouseInfo() {
            this.buttonMap = new int[GLFW.GLFW_MOUSE_BUTTON_LAST];
        }
        
        public double getX() {
            return this.x;
        }
        
        public double getY() {
            return this.y;
        }
    }*/
    
    private static final int KEY_NO_ACTION = 0;
    private static final int KEY_RELEASED = 1;
    private static final int KEY_PRESSED = 2;
    private static final int KEY_HELD = 3;
    
    private static final int SECTION_UPDATING = 0;
    private static final int SECTION_SNAPSHOT = 1;
    
    private static final int KEY_MAP_SIZE = GLFW.GLFW_KEY_LAST + 1;
    
    private final Window hostWindow;
    private final int[][] keyMap;
    private final int[][] mouseButtonMap;

    public Input(Window hostWindow) {
        this.hostWindow = hostWindow;
        this.keyMap = new int[2][KEY_MAP_SIZE];
        this.mouseButtonMap = new int [2][GLFW.GLFW_MOUSE_BUTTON_LAST + 1];
    }
    
    public void attach() {
        long windowID = this.hostWindow.getWindowID();
        GLFW.glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> keyListener(key, action));
        GLFW.glfwSetCursorPosCallback(windowID, (handle, xpos, ypos) -> mouseListener(xpos, ypos));
        GLFW.glfwSetMouseButtonCallback(windowID, (handle, button, action, mode) -> mouseListener(button, action));
    }
    
    public void snapshot() {
        final int[] updatingMap = this.keyMap[SECTION_UPDATING];
        final int[] snapshotMap = this.keyMap[SECTION_SNAPSHOT];
        
        for( int i = 0; i < KEY_MAP_SIZE; i++ )
        {
            snapshotMap[i] = updatingMap[i];
            
            if( updatingMap[i] == KEY_RELEASED )
            updatingMap[i] = KEY_NO_ACTION;
        }
    }
    
    public boolean isKeyPressed(int key) {
        return (this.keyMap[SECTION_SNAPSHOT][key] == KEY_PRESSED);
    }
    
    public boolean isKeyReleased(int key) {
        return (this.keyMap[SECTION_SNAPSHOT][key] == KEY_RELEASED);
    }
    
    public boolean isKeyDown(int key) {
        int keyState = this.keyMap[SECTION_SNAPSHOT][key];
        return (keyState == KEY_HELD || keyState == KEY_PRESSED);
    }
    
    private void keyListener(int key, int action) {
        if( action == GLFW.GLFW_REPEAT )
        return;
        
        this.keyMap[SECTION_UPDATING][key] = action + 1;
    }
    
    private void mouseListener(double mouseX, double mouseY) {
        
    }
    
    private void mouseListener(int button, int action) {
        
    }
}
