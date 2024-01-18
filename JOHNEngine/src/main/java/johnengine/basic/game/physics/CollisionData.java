package johnengine.basic.game.physics;

import johnengine.basic.game.physics.shapes.Shape;

public class CollisionData {
    public Shape collidedShape;
    public CollisionMesh collidedMesh;
    public PhysicsMaterial collidedMaterial;
    public float collisionDistanceNear;
    public float collisionDistanceFar;
}
