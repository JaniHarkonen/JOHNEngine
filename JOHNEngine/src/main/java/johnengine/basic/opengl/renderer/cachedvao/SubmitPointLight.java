package johnengine.basic.opengl.renderer.cachedvao;

import johnengine.basic.game.components.CAttenuation;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.opengl.renderer.cachedvao.structs.SAttenuation;
import johnengine.basic.opengl.renderer.cachedvao.structs.SPointLight;

public class SubmitPointLight extends ACachedVAOSubmission<JPointLight> {

    public SubmitPointLight(CachedVAORenderPass renderPass) {
        super(renderPass);
    }

    
    @Override
    public void execute(JPointLight instance) {
        SPointLight struct = new SPointLight();
        struct.c3Light = instance.getColor();
        struct.v3Position = instance.getTransform().getPosition().get();
        struct.fIntensity = instance.getIntensity();
        
        CAttenuation attenuation = instance.getAttenuation();
        SAttenuation attenuationStruct = new SAttenuation();
        attenuationStruct.fConstant = attenuation.getConstant();
        attenuationStruct.fLinear = attenuation.getLinear();
        attenuationStruct.fExponent = attenuation.getExponent();
        
        struct.attenuation = attenuationStruct;
        this.renderPass.getCurrentRenderBuffer().addPointLight(instance, struct);
    }
}
