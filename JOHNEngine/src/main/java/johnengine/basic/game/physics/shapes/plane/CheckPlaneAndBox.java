package johnengine.basic.game.physics.shapes.plane;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.CollisionData;
import johnengine.basic.game.physics.shapes.ICollisionCheck;

public class CheckPlaneAndBox implements ICollisionCheck {

    @Override
    public CollisionData checkCollision(
        CTransform t1, 
        float velocityX, 
        float velocityY, 
        float velocityZ, 
        CTransform t2
    ) {
        return false;
    }
}
