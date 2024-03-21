package johnengine.core.defaultobj;

public class Defaultable<T> {

    private T value;
    private T defaultValue;
    
    public Defaultable(T value, T defaultValue) {
        this.value = value;
        this.defaultValue = defaultValue;
    }
    
    public Defaultable(T defaultValue) {
        this(null, defaultValue);
    }
    
    
    public T get() {
        return this.value;
    }
    
    public T getDefault() {
        return this.defaultValue;
    }
    
    public boolean isNull() {
        return (this.value == this.defaultValue);
    }
    
    public void set(T value) {
        this.value = value;
    }
    
    public void reset() {
        this.value = this.defaultValue;
    }
}
