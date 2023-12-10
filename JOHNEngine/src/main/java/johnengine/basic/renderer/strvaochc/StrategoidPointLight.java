package johnengine.basic.renderer.strvaochc;

import johnengine.basic.game.lights.JPointLight;
import johnengine.core.renderer.ARenderBufferStrategoid;

public class StrategoidPointLight extends ARenderBufferStrategoid<JPointLight, CachedVAORenderBufferStrategy> {

    public StrategoidPointLight(CachedVAORenderBufferStrategy strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JPointLight instance) {
        this.strategy.addPointLight(instance);
    }
}
