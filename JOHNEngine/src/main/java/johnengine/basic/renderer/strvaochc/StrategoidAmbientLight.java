package johnengine.basic.renderer.strvaochc;

import johnengine.basic.game.lights.JAmbientLight;
import johnengine.core.renderer.ARenderBufferStrategoid;

public class StrategoidAmbientLight extends ARenderBufferStrategoid<JAmbientLight, CachedVAORenderBufferStrategy> {

    public StrategoidAmbientLight(CachedVAORenderBufferStrategy strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JAmbientLight instance) {
        this.strategy.setAmbientLight(instance);
    }
}
