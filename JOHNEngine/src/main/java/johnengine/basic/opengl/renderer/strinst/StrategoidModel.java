package johnengine.basic.opengl.renderer.strinst;

import johnengine.basic.game.components.CModel;
import johnengine.basic.opengl.renderer.strinst.InstancedRenderBuffer.TextureBuffer;
import johnengine.core.renderer.ARenderBufferStrategoid;

public class StrategoidModel extends ARenderBufferStrategoid<CModel, InstancedRenderBufferStrategy> {

    public StrategoidModel(InstancedRenderBufferStrategy strategy) {
        super(strategy);
    }
    
    @Override
    public void execute(CModel instance) {
        TextureBuffer textures = this.strategy.renderBuffer.getTextureBuffer(instance.getMesh());
        textures.addTexture(instance.getTexture()).add(instance.getPosition().get());
    }

    @Override
    public ARenderBufferStrategoid<CModel, InstancedRenderBufferStrategy> newInstance() {
        return new StrategoidModel(this.strategy);
    }
}
