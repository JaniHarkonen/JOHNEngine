package johnengine.basic.game.components.physics;

import johnengine.basic.game.components.geometry.rewrite.CPosition;
import johnengine.core.ITickable;

public class CForce implements ITickable {

    private CPosition target;
    
    public CForce(CPosition target) {
        this.target = target;
    }
    
    @Override
    public void tick(float deltaTime) {
        
    }
}
