package johnengine.basic.renderer.strvaochc;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderStrategoid;

public abstract class ACachedVAOStrategoid<T extends IRenderable> implements IRenderStrategoid<T> {

    protected CachedVAORenderStrategy strategy;
    
    public ACachedVAOStrategoid(CachedVAORenderStrategy strategy) {
        this.strategy = strategy;
    }
}
