package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SSpotLight;

public class SubmitSpotLight extends ACachedVAOSubmission<JSpotLight> {

    public SubmitSpotLight(CachedVAORenderPass renderPass) {
        super(renderPass);
    }

    
    @Override
    public void execute(JSpotLight instance) {
        SSpotLight struct = new SSpotLight();
        struct.v3Direction = instance.getConeDirection();
        struct.fCutOff = instance.getCutOff();
        
        RenderBuffer renderBuffer = this.renderPass.getCurrentRenderBuffer();
        struct.pointLight = 
            renderBuffer.getPointLightStruct(instance.getPointLight());
        
        renderBuffer.addSpotLight(instance, struct);
    }
}
