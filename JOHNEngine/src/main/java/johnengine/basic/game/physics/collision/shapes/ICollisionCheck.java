package johnengine.basic.game.physics.collision.shapes;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.collision.CollisionData;

public interface ICollisionCheck {

    public boolean checkCollision(
        CTransform t1, 
        Vector3f velocity, 
        CTransform t2,
        CollisionData result
    );
}
