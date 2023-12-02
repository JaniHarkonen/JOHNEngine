package johnengine.basic.renderer.strinst;

import johnengine.basic.game.CModel;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;

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
