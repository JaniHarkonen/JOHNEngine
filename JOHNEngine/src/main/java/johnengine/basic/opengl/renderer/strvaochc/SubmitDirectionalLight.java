package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SDirectionalLight;

public class SubmitDirectionalLight extends ACachedVAOSubmission<JDirectionalLight> {

    public SubmitDirectionalLight(CachedVAORenderPass strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JDirectionalLight instance) {
        SDirectionalLight struct = new SDirectionalLight();
        struct.c3Light = instance.getColor();
        struct.v3Direction = instance.getDirection();
        struct.fIntensity = instance.getIntensity();
        this.strategy.setDirectionalLight(struct);
    }
}
