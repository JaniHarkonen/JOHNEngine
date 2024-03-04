package johnengine.basic.opengl.renderer.asset;

import johnengine.basic.assets.IGeneratable;
import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.opengl.renderer.RendererGL;

public abstract class AGraphicsStrategyGL<T> implements IGraphicsStrategy, IGeneratable {

    protected final RendererGL renderer;
    protected T graphicsData;
    protected T actualGraphicsData;
    
    public AGraphicsStrategyGL(RendererGL renderer) {
        this.renderer = renderer;
    }
    
    
    public abstract T getDefault();
    
    public T get() {
        return this.graphicsData;
    }
    
    public T getUnsafe() {
        return this.actualGraphicsData;
    }
}
