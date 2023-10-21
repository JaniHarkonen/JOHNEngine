package johnengine.core.renderer.unimngr;

public abstract class AUniform<T> {

    private int location;
    private final String name;
    private T value;
    
    public AUniform(String name, T value) {
        this.name = name;
        this.value = value;
    }
    
    public AUniform(String name) {
        this(name, null);
    }
    
    
    public abstract void set();
    
    public void set(T value) {
        this.set(value);
        this.set();
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    void setLocation(int location) {
        this.location = location;
    }
    
    public String getName() {
        return this.name;
    }
    
    public T getValue() {
        return this.value;
    }
    
    int getLocation() {
        return this.location;
    }
}
