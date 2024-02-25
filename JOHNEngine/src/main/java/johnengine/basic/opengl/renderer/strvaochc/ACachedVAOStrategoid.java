package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderBufferStrategoid;

public abstract class ACachedVAOStrategoid<T extends IRenderable> implements IRenderBufferStrategoid<T> {

    protected CachedVAORenderStrategy strategy;
    
    public ACachedVAOStrategoid(CachedVAORenderStrategy strategy) {
        this.strategy = strategy;
    }
}
