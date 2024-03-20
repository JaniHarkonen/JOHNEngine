package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SAmbientLight;

public class SubmitAmbientLight extends ACachedVAOSubmission<JAmbientLight> {

    public SubmitAmbientLight(CachedVAORenderPass strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JAmbientLight instance) {
        SAmbientLight struct = new SAmbientLight();
        struct.c3Ambient = instance.getColor();
        struct.fIntensity = instance.getIntensity();
        
        this.strategy.setAmbientLight(struct);
    }
}
