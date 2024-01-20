package johnengine.basic.game.physics.collision.shapes.sphere;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.collision.CollisionData;
import johnengine.basic.game.physics.collision.shapes.ICollisionCheck;

public class CheckSphereAndSphere implements ICollisionCheck {

    @Override
    public boolean checkCollision(
        CTransform t1, 
        float velocityX, 
        float velocityY, 
        float velocityZ,
        CTransform t2,
        CollisionData result
    ) {
        return false;
    }
}
