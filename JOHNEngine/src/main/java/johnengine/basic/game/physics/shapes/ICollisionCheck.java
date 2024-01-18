package johnengine.basic.game.physics.shapes;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.CollisionData;

public interface ICollisionCheck {

    public CollisionData checkCollision(
        CTransform t1, 
        float velocityX, 
        float velocityY, 
        float velocityZ, 
        CTransform t2
    );
    
    public default CollisionData checkCollision(CTransform t1, Vector3f velocity, CTransform t2) {
        return this.checkCollision(t1, velocity.x, velocity.y, velocity.z, t2);
    }
}
