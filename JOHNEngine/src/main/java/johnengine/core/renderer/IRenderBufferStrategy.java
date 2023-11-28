package johnengine.core.renderer;

public interface IRenderBufferStrategy {

    public abstract void prepare();
    
    public abstract void execute(IDrawable drawable);
    
    public abstract void dispose();
    
    public abstract <T extends IDrawable> ARenderBufferStrategoid<T, ? extends ARenderBufferStrategy> getStrategoid(T instance);
}
