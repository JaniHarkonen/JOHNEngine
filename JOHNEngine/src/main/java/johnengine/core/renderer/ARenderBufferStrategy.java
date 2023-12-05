package johnengine.core.renderer;

import java.util.HashMap;
import java.util.Map;

import johnengine.basic.assets.IRendererAsset;
import johnengine.core.IRenderBufferStrategy;
import johnengine.core.IRenderable;
import johnengine.core.assetmngr.asset.IDeloadProcessor;
import johnengine.core.assetmngr.asset.ILoaderMonitor;

public abstract class ARenderBufferStrategy implements 
    IRenderBufferStrategy,
    ILoaderMonitor<IRendererAsset>,
    IDeloadProcessor<IRendererAsset>
{
    
    protected final 
        Map<
            Class<?>, 
            ARenderBufferStrategoid<
                ? extends IRenderable, 
                ? extends ARenderBufferStrategy
            >
        > strategoidMap;
    
    protected final ARenderer renderer;
    
    protected ARenderBufferStrategy(ARenderer renderer) {
        this.strategoidMap = new HashMap<>();
        this.renderer = renderer;
    }
    
    
    protected void addStrategoid(
            Class<?> clazz, 
            ARenderBufferStrategoid<
                ? extends IRenderable, 
                ? extends ARenderBufferStrategy
            > renderBufferStrategoid) 
    {
        this.strategoidMap.put(clazz, renderBufferStrategoid);
    }
    
    public abstract void render();

    @Override
    @SuppressWarnings("unchecked")
    public <I extends IRenderable> void executeStrategoid(I instance) {
        ARenderBufferStrategoid<I, ?> strategoid = (ARenderBufferStrategoid<I, ?>) (
            this.strategoidMap.get(instance.getClass())
        );
        strategoid.execute(instance);
    }
    
    
    public ARenderer getRenderer() {
        return this.renderer;
    }
}
