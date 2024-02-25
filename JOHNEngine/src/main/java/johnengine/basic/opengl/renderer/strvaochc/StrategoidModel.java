package johnengine.basic.opengl.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.components.CModel;

public class StrategoidModel extends ACachedVAOStrategoid<CModel> {

    public StrategoidModel(CachedVAORenderStrategy strategy) {
        super(strategy);
    }

    @Override
    public void execute(CModel instance) {
        RenderUnit unit = new RenderUnit(
            instance.getMesh(), 
            instance.getTexture(),
            new Matrix4f(instance.getTransform().get())
        );
        
        this.strategy.addRenderUnit(unit);
    }
}
