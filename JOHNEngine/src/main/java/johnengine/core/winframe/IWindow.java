package johnengine.core.winframe;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import johnengine.core.input.IInput;
import johnengine.core.renderer.IRenderer;

public interface IWindow {
    
    /*********************** Properties-class ***********************/
    
    public final class Properties {
        public static final String POSITION = "window-position";
        public static final String SIZE = "window-size";
        public static final String MONITOR_SIZE = "monitor-size";
        public static final String TITLE = "title";
        public static final String FPS = "fps";
        public static final String HAS_BORDER = "has-border";
        public static final String IS_CURSOR_LOCKED_TO_CENTER = "is-cursor-locked-to-center";
        public static final String IS_CURSOR_VISIBLE = "is-cursor-visible";
        public static final String USE_V_SYNC = "use-v-sync";
        public static final String IS_FOCUSED = "is-focused";
        public static final String IS_MAXIMIZED = "is-maxmimized";
        public static final String IS_IN_FULLSCREEN = "is-in-fullscreen";
        public static final String WINDOW_STATE = "window-state";
        
        public Property<Point> position;
        public Property<Point> size;
        public Property<Point> monitorSize;
        public Property<String> title;
        public Property<Long> fps;
        public Property<Boolean> hasBorder;
        public Property<Boolean> isCursorLockedToCenter;
        public Property<Boolean> isCursorVisible;
        public Property<Boolean> useVSync;
        public Property<Boolean> isFocused;
        public Property<Boolean> isMaximized;
        public Property<Boolean> isInFullscreen;
        public Property<Integer> windowState;
        
        private Map<String, Property<?>> properties;
        
        public Properties() {
            this.position = new Property<>(POSITION, new Point(64, 64));
            this.size = new Property<>(SIZE, new Point(640, 480));
            this.monitorSize = new Property<>(MONITOR_SIZE, new Point(0, 0));
            this.title = new Property<>(TITLE, "Powered by JOHNEngine v.1.0.0");
            this.fps = new Property<>(FPS, (long) 0);
            this.hasBorder = new Property<>(HAS_BORDER, true);
            this.isCursorLockedToCenter = new Property<>(IS_CURSOR_LOCKED_TO_CENTER, false);
            this.isCursorVisible = new Property<>(IS_CURSOR_VISIBLE, true);
            this.useVSync = new Property<>(USE_V_SYNC, false);
            this.isFocused = new Property<>(IS_FOCUSED, true);
            this.isMaximized = new Property<>(IS_MAXIMIZED, false);
            this.isInFullscreen = new Property<>(IS_IN_FULLSCREEN, false);
            this.windowState = new Property<>(WINDOW_STATE, IWindow.STATE_INITIALIZING);
            
            this.properties = new HashMap<>();
            this.storeProperty(this.position);
            this.storeProperty(this.size);
            this.storeProperty(this.monitorSize);
            this.storeProperty(this.title);
            this.storeProperty(this.fps);
            this.storeProperty(this.hasBorder);
            this.storeProperty(this.isCursorLockedToCenter);
            this.storeProperty(this.isCursorVisible);
            this.storeProperty(this.useVSync);
            this.storeProperty(this.isFocused);
            this.storeProperty(this.isMaximized);
            this.storeProperty(this.isInFullscreen);
            this.storeProperty(this.windowState);
        }
        
        
        private void storeProperty(Property<?> property) {
            this.properties.put(property.getKey(), property);
        }
        
        public Property<?> getProperty(String propertyKey) {
            return this.properties.get(propertyKey);
        }
        
        public void snapshot() {
            for( Map.Entry<String, Property<?>> en : this.properties.entrySet() )
            en.getValue().snapshot();
        }
    }
    
    
    /*********************** Property-class ***********************/
    
    public class Property<T> {
        public String key;
        public T lastValue;
        public T currentValue;
        public T defaultValue;
        
        public Property(String key, T defaultValue) {
            this.key = key;
            this.lastValue = defaultValue;
            this.currentValue = defaultValue;
            this.defaultValue = defaultValue;
        }
        
        
        public void snapshot() {
            this.lastValue = this.currentValue;
        }
        
        public void reset() {
            this.currentValue = this.defaultValue;
        }
        
        public void set(T value) {
            this.currentValue = value;
        }
        
        public T getLast() {
            return this.lastValue;
        }
        
        public T getCurrent() {
            return this.currentValue;
        }
        
        public T getDefault() {
            return this.defaultValue;
        }
        
        public String getKey() {
            return this.key;
        }
    }
    
    
    /**
     * Window is being initialized, and is not yet processing 
     * requests nor rendering anything. Some sub-components
     * may not be instantiated yet.
     */
    public static final int STATE_INITIALIZING = 1;
    
    /**
     * Window was closed and is no longer processing requests.
     * All sub-components have been disposed and nullified.
     */
    public static final int STATE_CLOSED = 2;
    
    /**
     * Window has been initialized, along with its sub-components, 
     * and is now open and ready to process requests.
     */
    public static final int STATE_OPEN = 3;
    
    
    /*********************** REQUESTS ***********************/
    
    public IWindow move(int x, int y);
    
    public IWindow resize(int width, int height);
    
    public IWindow changeTitle(String title);
    
    public IWindow showBorder();
    
    public IWindow hideBorder();
    
    public IWindow lockCursorToCenter();
    
    public IWindow releaseCursor();
    
    public IWindow showCursor();
    
    public IWindow hideCursor();
    
    public IWindow enableVSync();
    
    public IWindow disableVSync();
    
    public IWindow enterFullscreen();
    
    public IWindow exitFullscreen();
    
    
    /*********************** GETTERS ***********************/
    
    public int getX();
    
    public int getY();
    
    public int getWidth();
    
    public int getHeight();
    
    public String getTitle();
    
    public long getFPS();
    
    public boolean hasBorder();
    
    public boolean isCursorLockedToCenter();
    
    public boolean isCursorVisible();
    
    public boolean useVSync();
    
    public boolean isFocused();
    
    public boolean isMaximized();
    
    public boolean isInFullscreen();
    
    public int getWindowState();
    
    public Object getProperty(String propertyKey);
    
    public boolean hasWindowClosed();
    
    public boolean isWindowClosing();
    
    public boolean isWindowInitializing();
    
    public boolean isWindowOpen();
    
    public IInput getInput();
    
    public IRenderer getRenderer();
    
    
    /*********************** SETTERS ***********************/
    
    public void setRenderer(IRenderer renderer);
}
