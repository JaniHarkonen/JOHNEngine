package johnengine.testing;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.rewrite.CModel;
import johnengine.basic.game.JWorld;
import johnengine.core.renderer.ARenderer;

public class JTestBox extends AWorldObject {

    private CModel model;
    
    public JTestBox(JWorld world, CModel model) {
        super(world);
        this.model = model;
    }
    

    @Override
    public void render(ARenderer renderer) {
        this.model.render(renderer);
    }

    @Override
    public void tick(float deltaTime) {
        
    }
}
