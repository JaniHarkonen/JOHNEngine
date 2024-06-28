package johnengine.basic.opengl.input;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import johnengine.basic.opengl.WindowGL;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInput;
import johnengine.core.window.IWindow;

public final class MouseKeyboardInputGL implements IInput {
    
    
    /***************************** KeyPressed-class ****************************/
    
    private static final int KEY_PRESSED_HASH = 1;
    
    public static final class KeyPressed extends AInputEvent<Boolean> {
        
        public final int key;
        
        public KeyPressed(int key) {
            super(MouseKeyboardInputGL.KEY_PRESSED_HASH);
            this.key = key;
        }

        
        @Override
        public Boolean getValue() {
            return true;
        }
        
        @Override
        protected boolean equalsImpl(Object obj) {
            if( obj instanceof KeyPressed )
            return (KeyPressed.class.cast(obj).key == this.key);
            
            return false;
        }
        
    }
    
    
    /***************************** KeyHeld-class ****************************/
    
    private static final int KEY_HELD_HASH = 2;
    
    public static final class KeyHeld extends AInputEvent<Boolean> {
        
        public final int key;
        
        public KeyHeld(int key) {
            super(MouseKeyboardInputGL.KEY_HELD_HASH);
            this.key = key;
        }
        

        @Override
        public Boolean getValue() {
            return true;
        }

        @Override
        protected boolean equalsImpl(Object obj) {
            if( obj instanceof KeyHeld )
            return (KeyHeld.class.cast(obj).key == this.key);
            
            return false;
        }
    }
    
    
    /***************************** KeyReleased-class ****************************/
    
    private static final int KEY_RELEASED_HASH = 3;
    
    public static final class KeyReleased extends AInputEvent<Boolean> {

        public final int key;
        
        public KeyReleased(int key) {
            super(MouseKeyboardInputGL.KEY_RELEASED_HASH);
            this.key = key;
        }
        

        @Override
        public Boolean getValue() {
            return true;
        }
        
        @Override
        protected boolean equalsImpl(Object obj) {
            if( obj instanceof KeyReleased )
            return (KeyReleased.class.cast(obj).key == this.key);
            
            return false;
        }
    }
    
    
    /***************************** MousePressed-class ****************************/
    
    private static final int MOUSE_PRESSED_HASH = 4;
    
    public static final class MousePressed extends AInputEvent<Boolean> {

        public final int mouseButton;
        
        public MousePressed(int mouseButton) {
            super(MouseKeyboardInputGL.MOUSE_PRESSED_HASH);
            this.mouseButton = mouseButton;
        }
        

        @Override
        public Boolean getValue() {
            return true;
        }

        @Override
        protected boolean equalsImpl(Object obj) {
            if( obj instanceof MousePressed )
            return (MousePressed.class.cast(obj).mouseButton == this.mouseButton);
            
            return false;
        }
    }
    
    
    /***************************** MouseHeld-class ****************************/
    
    private static final int MOUSE_HELD_HASH = 5;
    
    public static final class MouseHeld extends AInputEvent<Boolean> {

        public final int mouseButton;
        
        public MouseHeld(int mouseButton) {
            super(MouseKeyboardInputGL.MOUSE_HELD_HASH);
            this.mouseButton = mouseButton;
        }
        

        @Override
        public Boolean getValue() {
            return true;
        }

        @Override
        protected boolean equalsImpl(Object obj) {
            if( obj instanceof MouseHeld )
            return (MouseHeld.class.cast(obj).mouseButton == this.mouseButton);
            
            return false;
        }
    }
    
    
    /***************************** MouseReleased-class ****************************/
    
    private static final int MOUSE_RELEASED_HASH = 6;
    
    public static final class MouseReleased extends AInputEvent<Boolean> {

        public final int mouseButton;
        
        public MouseReleased(int mouseButton) {
            super(MouseKeyboardInputGL.MOUSE_RELEASED_HASH);
            this.mouseButton = mouseButton;
        }
        

        @Override
        public Boolean getValue() {
            return true;
        }

        @Override
        protected boolean equalsImpl(Object obj) {
            if( obj instanceof MouseReleased )
            return (MouseReleased.class.cast(obj).mouseButton == this.mouseButton);
            
            return false;
        }
    }
    
    
    /***************************** MouseMove-class ****************************/
    
    private static final int MOUSE_MOVE_HASH = 7;
    
    public static final class MouseMove extends AInputEvent<Point2D.Double> {

        public final Point2D.Double mousePosition;
        
        public MouseMove(double mouseX, double mouseY) {
            super(MouseKeyboardInputGL.MOUSE_MOVE_HASH);
            this.mousePosition = new Point2D.Double(mouseX, mouseY);
        }
        
        public MouseMove() {
            super(MouseKeyboardInputGL.MOUSE_MOVE_HASH);
            this.mousePosition = null;
        }
        

        @Override
        public Point2D.Double getValue() {
            return this.mousePosition;
        }
        
        @Override
        protected boolean equalsImpl(Object obj) {
            return (obj instanceof MouseMove);
        }
    }
    
    
    /***************************** MouseKeyboardInputGL-class ****************************/
 
    private static final int NONE = 0;
    private static final int PRESSED = 1;
    private static final int HELD = 2;
    private static final int RELEASED = 3;
    
    private static final int KEY_MAP_SIZE = 
        GLFW.GLFW_KEY_LAST + 1;
    private static final int MOUSE_BUTTON_MAP_SIZE = 
        GLFW.GLFW_MOUSE_BUTTON_LAST + 1;
    
    private final WindowGL hostWindow;
    private final int[] keyMap;
    private final int[] mouseButtonMap;
    private Queue<AInputEvent<?>> eventList;
    private List<AInputEvent<?>> lastEventList;
    
    public MouseKeyboardInputGL(WindowGL hostWindow) {
        this.hostWindow = hostWindow;
        this.keyMap = new int[KEY_MAP_SIZE];
        this.mouseButtonMap = new int[MOUSE_BUTTON_MAP_SIZE];
        this.eventList = new ConcurrentLinkedQueue<>();
        this.lastEventList = new ArrayList<>();
    }
    
    
    @Override
    public void setup() {
        long windowID = this.hostWindow.getWindowID();
        
            // Set keyboard callback
        GLFWKeyCallbackI keyCallback = (window, key, scancode, action, mods) -> {
            keyListener(key, action);
        };
        GLFW.glfwSetKeyCallback(windowID, keyCallback);
        
            // Set mouse move callback
        GLFWCursorPosCallbackI cursorPosCallback = (handle, xpos, ypos) -> {
            mousePositionListener(xpos, ypos);
        };
        GLFW.glfwSetCursorPosCallback(windowID, cursorPosCallback);
        
            // Set mouse button callback
        GLFWMouseButtonCallbackI mouseButtonCallback = (handle, button, action, mode) -> {
            mouseListener(button, action);
        };
        GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
        
        //GLFW.glfwSetScrollCallback(window, cbfun)
    }

    @Override
    public void update() {
        for( int i = 0; i < KEY_MAP_SIZE; i++ )
        {
            int keyValue = this.keyMap[i];
            
            if( keyValue == PRESSED || keyValue == HELD )
            {
                this.keyMap[i] = HELD;
                this.recordEvent(new KeyHeld(i));
            }
            else if( keyValue == RELEASED )
            this.keyMap[i] = NONE;
        }
        
        for( int i = 0; i < MOUSE_BUTTON_MAP_SIZE; i++ )
        {
            int buttonValue = this.mouseButtonMap[i];
            
            if( buttonValue == PRESSED )
            {
                this.mouseButtonMap[i] = HELD;
                this.recordEvent(new MouseReleased(i));
            }
            else if( buttonValue == RELEASED )
            this.mouseButtonMap[i] = NONE;
        }
            
        GLFW.glfwPollEvents();
    }
    
    private void recordEvent(AInputEvent<?> event) {
        this.eventList.add(event);
    }

    @Override
    public void pollEvents() {
        this.lastEventList = new ArrayList<>();
        
        AInputEvent<?> event;
        while( (event = this.eventList.poll()) != null )
        this.lastEventList.add(event);
        
        this.lastEventList = Collections.unmodifiableList(this.lastEventList);
        this.eventList = new ConcurrentLinkedQueue<>();
    }
    
    @Override
    public void dispose() {
        long windowID = this.hostWindow.getWindowID();
        GLFW.glfwSetKeyCallback(windowID, null);
        GLFW.glfwSetCursorPosCallback(windowID, null);
        GLFW.glfwSetMouseButtonCallback(windowID, null);
        GLFW.glfwSetScrollCallback(windowID, null);
    }

    
    /***************************** LISTENERS ****************************/
    
    private void keyListener(int key, int action) {
        if( action == GLFW.GLFW_PRESS )
        {
            this.keyMap[key] = PRESSED;
            this.recordEvent(new KeyPressed(key));
        }
        else if( action == GLFW.GLFW_RELEASE )
        {
            this.keyMap[key] = RELEASED;
            this.recordEvent(new KeyReleased(key));
        }
    }
    
    private void mousePositionListener(double mouseX, double mouseY) {
        this.eventList.add(new MouseMove(mouseX, mouseY));
    }
    
    private void mouseListener(int button, int action) {
        if( action == GLFW.GLFW_PRESS )
        {
            this.mouseButtonMap[button] = PRESSED;
            this.recordEvent(new MousePressed(button));
        }
        else if( action == GLFW.GLFW_RELEASE )
        {
            this.mouseButtonMap[button] = RELEASED;
            this.recordEvent(new MouseReleased(button));
        }
    }
    
    
    /***************************** SETTERS ****************************/

    @Override
    public List<AInputEvent<?>> getEvents() {
        return this.lastEventList;
    }

    @Override
    public IWindow getWindow() {
        return this.hostWindow;
    }
}
