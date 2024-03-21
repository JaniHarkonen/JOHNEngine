package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SAmbientLight;

public class SubmitAmbientLight extends ACachedVAOSubmission<JAmbientLight> {

    public SubmitAmbientLight(CachedVAORenderPass renderPass) {
        super(renderPass);
    }

    
    @Override
    public void execute(JAmbientLight instance) {
        SAmbientLight struct = new SAmbientLight();
        struct.c3Ambient = instance.getColor();
        struct.fIntensity = instance.getIntensity();
        
        this.renderPass.getCurrentRenderBuffer().setAmbientLight(struct);
    }
}
