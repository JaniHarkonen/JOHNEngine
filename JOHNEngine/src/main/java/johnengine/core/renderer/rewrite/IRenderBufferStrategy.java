package johnengine.core.renderer.rewrite;

import johnengine.core.rewrite.IDrawable;

public interface IRenderBufferStrategy {

    public abstract void prepare();
    
    public abstract void dispose();
    
    public abstract <T extends IDrawable> ARenderBufferStrategoid<T, ? extends ARenderBufferStrategy> getStrategoid(T instance);
}
