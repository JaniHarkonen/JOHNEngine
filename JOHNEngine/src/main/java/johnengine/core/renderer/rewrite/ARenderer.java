package johnengine.core.renderer.rewrite;

import johnengine.core.renderer.ARenderBufferStrategoid;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.IDrawable;
import johnengine.core.winframe.AWindowFramework;

public abstract class ARenderer {
    
    protected final ARenderBufferStrategy renderBufferStrategy;
    protected AWindowFramework hostWindow;
    
    protected ARenderer(
        AWindowFramework hostWindow,
        ARenderBufferStrategy renderBufferStrategy
    ) {
        this.hostWindow = hostWindow;
        this.renderBufferStrategy = renderBufferStrategy;
    }

    public abstract void generateDefaults();
    
    public abstract void initialize();
    
    public abstract void generateRenderBuffer();
    
    public abstract void render();
    
    
    public AWindowFramework getWindow() {
        return this.hostWindow;
    }
    
    public <T extends IDrawable> ARenderBufferStrategoid<T, ? extends ARenderBufferStrategy> getRenderBufferStrategoid(T instance) {
        return this.renderBufferStrategy.getStrategoid(instance);
    }
}
