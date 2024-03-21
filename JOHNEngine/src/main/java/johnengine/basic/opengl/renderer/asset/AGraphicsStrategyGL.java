package johnengine.basic.opengl.renderer.asset;

import johnengine.basic.assets.IGeneratable;
import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.core.defaultobj.Defaultable;

public abstract class AGraphicsStrategyGL<T> implements IGraphicsStrategy, IGeneratable {

    protected final RendererGL renderer;
    protected Defaultable<T> graphics;
    protected boolean isPersistent;
    
    public AGraphicsStrategyGL(RendererGL renderer, boolean isPersistent) {
        this.renderer = renderer;
        T defaultGraphics = this.getDefaultGraphics();
        this.graphics = new Defaultable<>(defaultGraphics, defaultGraphics);
        this.isPersistent = isPersistent;
    }
    
    
    @Override
    public void deload() {
        if( this.isPersistent )
        return;
        
        this.deloadImpl();
        this.graphics.reset();
    }
    
    protected abstract void deloadImpl();
    
    public abstract T getDefaultGraphics();
    
    public T getGraphics() {
        return this.graphics.get();
    }
}
