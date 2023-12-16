package johnengine.basic.renderer.strvaochc;

import johnengine.basic.game.components.CAttenuation;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.renderer.strvaochc.structs.SAttenuation;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;
import johnengine.core.renderer.ARenderBufferStrategoid;

public class StrategoidPointLight extends ARenderBufferStrategoid<JPointLight, CachedVAORenderBufferStrategy> {

    public StrategoidPointLight(CachedVAORenderBufferStrategy strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JPointLight instance) {
        SPointLight struct = new SPointLight();
        struct.c3Light = instance.getColor();
        struct.v3Position = instance.getPosition().get();
        struct.fIntensity = instance.getIntensity();
        
        CAttenuation attenuation = instance.getAttenuation();
        SAttenuation attenuationStruct = new SAttenuation();
        attenuationStruct.fConstant = attenuation.getConstant();
        attenuationStruct.fLinear = attenuation.getLinear();
        attenuationStruct.fExponent = attenuation.getExponent();
        
        struct.attenuation = attenuationStruct;
        this.strategy.addPointLight(instance, struct);
    }
}
