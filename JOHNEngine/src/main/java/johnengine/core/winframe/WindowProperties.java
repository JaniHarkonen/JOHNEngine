package johnengine.core.winframe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import johnengine.testing.DebugUtils;

public class WindowProperties {
    public static final String WINDOW_SIZE = "window-size";
    public static final String WINDOW_POSITION = "window-position";
    public static final String MONITOR_WIDTH = "monitor-width";
    public static final String MONITOR_HEIGHT = "monitor-height";
    public static final String FPS = "fps";
    public static final String TITLE = "title";
    
    public static final String HAS_BORDER = "has-border";
    public static final String LOCK_CURSOR_TO_CENTER = "lock-cursor-to-center";
    public static final String IS_CURSOR_VISIBLE = "is-cursor-visible";
    public static final String WINDOW_STATE = "window-state";
    public static final String USE_V_SYNC = "use-v-sync";
    public static final String IS_FOCUSED = "is-focused";
    public static final String IS_MAXIMIZED = "is-maximized";
    public static final String IS_FULLSCREEN = "is-fullscreen";

    public static class Property {
        final String key;
        Consumer<Object> action;
        Object defaultValue;
        Object currentValue;
        Object snapshotValue;
        
        public Property(String key, Object defaultValue, Consumer<Object> action) {
            this.key = key;
            this.action = action;
            this.defaultValue = defaultValue;
            this.currentValue = defaultValue;
            this.snapshotValue = defaultValue;
        }
        
        public Property(String key, Object defaultValue) {
            this(key, defaultValue, (requestValue) -> {});
        }
        
        
        public void completeRequest(Object value) {
            this.action.accept(this.currentValue);
            this.currentValue = value;
        }
        
        public void snapshot() {
            this.snapshotValue = this.currentValue;
        }
        
        public void reset() {
            this.currentValue = this.defaultValue;
        }
        
        public Object get() {
            return this.snapshotValue;
        }
        
        public Object getCurrent() {
            return this.currentValue;
        }
        
        public void setAction(Consumer<Object> action) {
            this.action = action;
        }
        
        public void setCurrent(Object currentValue) {
            this.currentValue = currentValue;
        }
    }
    
    
    private Map<String, Property> properties;
    
    public WindowProperties() {
        this.properties = new HashMap<>();
    }
    
    
    public void addProperty(Property property) {
        if( this.properties.get(property.key) != null )
        DebugUtils.log(this, "ERROR: Trying to add an already existing property!");
        else
        this.properties.put(property.key, property);
    }
    
    public void snapshot() {
        for( Map.Entry<String, Property> en : this.properties.entrySet() )
        en.getValue().snapshot();
    }
    
    public WindowProperties.Property getProperty(String key) {
        return this.properties.get(key);
    }
}
