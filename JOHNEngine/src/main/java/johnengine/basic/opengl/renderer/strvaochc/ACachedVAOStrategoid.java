package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderBufferStrategoid;

public abstract class ACachedVAOStrategoid<T extends IRenderable> implements IRenderBufferStrategoid<T> {

    protected CachedVAORenderPass strategy;
    
    public ACachedVAOStrategoid(CachedVAORenderPass strategy) {
        this.strategy = strategy;
    }
}
