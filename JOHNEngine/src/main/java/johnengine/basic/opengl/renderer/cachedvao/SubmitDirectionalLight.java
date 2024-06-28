package johnengine.basic.opengl.renderer.cachedvao;

import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.opengl.renderer.cachedvao.structs.SDirectionalLight;

public class SubmitDirectionalLight extends ACachedVAOSubmission<JDirectionalLight> {

    public SubmitDirectionalLight(CachedVAORenderPass renderPass) {
        super(renderPass);
    }

    
    @Override
    public void execute(JDirectionalLight instance) {
        SDirectionalLight struct = new SDirectionalLight();
        struct.c3Light = instance.getColor();
        struct.v3Direction = instance.getDirection();
        struct.fIntensity = instance.getIntensity();
        this.renderPass.getCurrentRenderBuffer().setDirectionalLight(struct);
    }
}
