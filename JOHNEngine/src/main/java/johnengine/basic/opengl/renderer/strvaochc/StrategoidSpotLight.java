package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SSpotLight;

public class StrategoidSpotLight extends ACachedVAOStrategoid<JSpotLight> {

    public StrategoidSpotLight(CachedVAORenderPass strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JSpotLight instance) {
        SSpotLight struct = new SSpotLight();
        struct.v3Direction = instance.getConeDirection();
        struct.fCutOff = instance.getCutOff();
        struct.pointLight = this.strategy.getPointLightStruct(instance.getPointLight());
        this.strategy.addSpotLight(instance, struct);
    }
}
