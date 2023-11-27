package johnengine.basic.renderer.rewrite.strvaochc;

import johnengine.basic.game.rewrite.CModel;
import johnengine.basic.renderer.rewrite.strvaochc.CachedVAORenderBufferStrategy.RenderUnit;
import johnengine.core.renderer.rewrite.ARenderBufferStrategoid;

public class StrategoidModel extends ARenderBufferStrategoid<CModel, CachedVAORenderBufferStrategy> {

    protected StrategoidModel(CachedVAORenderBufferStrategy strategy) {
        super(strategy);
    }

    @Override
    public void execute(CModel instance) {
        RenderUnit unit = new RenderUnit(
            instance.getMesh(), 
            instance.getTexture(), 
            instance.getPosition().getCopy()
        );
        
        this.strategy.addRenderUnit(unit);
    }

    @Override
    public ARenderBufferStrategoid<CModel, CachedVAORenderBufferStrategy> newInstance() {
        return new StrategoidModel(this.strategy);
    }
}
