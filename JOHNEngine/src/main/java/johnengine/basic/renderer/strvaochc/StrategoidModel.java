package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.CModel;
import johnengine.core.renderer.ARenderBufferStrategoid;

public class StrategoidModel extends ARenderBufferStrategoid<CModel, CachedVAORenderBufferStrategy> {

    public StrategoidModel(CachedVAORenderBufferStrategy strategy) {
        super(strategy);
    }

    @Override
    public void execute(CModel instance) {
        
            // Calculate position matrix
        Matrix4f positionMatrix = (new Matrix4f())
        .translationRotateScale(
            instance.getPosition().get(),
            instance.getRotation().get(),
            instance.getScale().get()
        );
        
        RenderUnit unit = new RenderUnit(
            instance.getMesh(), 
            instance.getTexture(), 
            positionMatrix
        );
        
        this.strategy.addRenderUnit(unit);
    }
}
