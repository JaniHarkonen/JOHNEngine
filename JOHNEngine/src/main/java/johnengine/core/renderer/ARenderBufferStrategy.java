package johnengine.core.renderer;

import java.util.HashMap;
import java.util.Map;

import johnengine.basic.assets.IRenderAsset;
import johnengine.core.IRenderBufferStrategy;
import johnengine.core.IRenderable;
import johnengine.core.assetmngr.asset.ILoaderMonitor;

public abstract class ARenderBufferStrategy implements 
    IRenderBufferStrategy,
    ILoaderMonitor<IRenderAsset> 
{
    
    protected final Map<Class<?>, ARenderBufferStrategoid<? extends IRenderable, ? extends ARenderBufferStrategy>> strategoidMap;
    
    protected ARenderBufferStrategy() {
        this.strategoidMap = new HashMap<Class<?>, ARenderBufferStrategoid<? extends IRenderable, ? extends ARenderBufferStrategy>>();
    }
    
    
    protected void addStrategoid(Class<?> clazz, ARenderBufferStrategoid<? extends IRenderable, ? extends ARenderBufferStrategy> renderBufferStrategoid) {
        this.strategoidMap.put(clazz, renderBufferStrategoid);
    }
    
    public abstract void render(ARenderer renderer);
    
    public abstract void disposeAsset(IRenderAsset asset);

    @Override
    @SuppressWarnings("unchecked")
    public <I extends IRenderable> void executeStrategoid(I instance) {
        ARenderBufferStrategoid<I, ?> strategoid = (ARenderBufferStrategoid<I, ?>) this.strategoidMap.get(instance.getClass());
        strategoid.execute(instance);
    }
}
