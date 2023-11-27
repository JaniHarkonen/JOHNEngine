package johnengine.basic.renderer.rewrite.strinst;

import johnengine.basic.game.rewrite.CModel;
import johnengine.core.renderer.rewrite.ARenderBufferStrategy;
import johnengine.core.renderer.rewrite.ARenderer;

public class InstancedRenderBufferStrategy extends ARenderBufferStrategy {
    InstancedRenderBuffer renderBuffer;

    public InstancedRenderBufferStrategy() {
        super();
        this.addStrategoid(CModel.class, new StrategoidModel(this));
        this.renderBuffer = new InstancedRenderBuffer();
    }

    
    @Override
    public void prepare() {
        
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public void render(ARenderer renderer) {
        
    }
}
