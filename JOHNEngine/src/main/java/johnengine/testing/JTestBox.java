package johnengine.testing;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CModel;
import johnengine.core.IRenderBufferStrategy;

public class JTestBox extends AWorldObject {

    private CModel model;
    
    public JTestBox(JWorld world, CModel model) {
        super(world);
        this.model = model;
        this.scale.set(new Vector3f(.25f));
    }
    

    @Override
    public void render(IRenderBufferStrategy renderBufferStrategy) {
        this.model.render(renderBufferStrategy);
    }

    @Override
    public void tick(float deltaTime) {
        //this.rotation.rotateY(0.001f);
    }
}
