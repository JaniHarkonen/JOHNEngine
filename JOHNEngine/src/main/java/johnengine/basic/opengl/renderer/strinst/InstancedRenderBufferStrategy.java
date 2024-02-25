package johnengine.basic.opengl.renderer.strinst;

import johnengine.basic.game.components.CModel;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARendererOLD;

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
    public void render(ARendererOLD renderer) {
        
    }
}
