package johnengine.core.renderer;

public abstract class ARenderBufferStrategoid<T, ST extends ARenderBufferStrategy> {
    
    protected ST strategy;
    
    protected ARenderBufferStrategoid(ST strategy) {
        this.strategy = strategy;
    }

    
    public abstract void execute(T instance);
    
    public abstract ARenderBufferStrategoid<T, ST> newInstance();
}
