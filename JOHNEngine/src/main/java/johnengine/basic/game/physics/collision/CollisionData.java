package johnengine.basic.game.physics.collision;

import johnengine.basic.game.physics.PhysicsMaterial;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class CollisionData {
    public boolean didCollide               = false;
    public Shape collidedShape              = null;
    public CollisionMesh collidedMesh       = null;
    public PhysicsMaterial collidedMaterial = null;
    public float collisionDistanceNear      = -1.0f;
    public float collisionDistanceFar       = -1.0f;
}
