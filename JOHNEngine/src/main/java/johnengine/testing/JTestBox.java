package johnengine.testing;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.CModel;
import johnengine.basic.game.JWorld;
import johnengine.core.IRenderBufferStrategy;

public class JTestBox extends AWorldObject {

    private CModel model;
    
    public JTestBox(JWorld world, CModel model) {
        super(world);
        this.model = model;
    }
    

    @Override
    public void render(IRenderBufferStrategy renderBufferStrategy) {
        this.model.render(renderBufferStrategy);
    }

    @Override
    public void tick(float deltaTime) {
        
    }
}
