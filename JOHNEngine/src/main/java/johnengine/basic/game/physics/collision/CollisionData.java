package johnengine.basic.game.physics.collision;

import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.basic.game.physics.PhysicsMaterial;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class CollisionData {
    public boolean didCollide               = false;
    public Shape collidedShape              = null;
    public CollisionMesh collidedMesh       = null;
    public PhysicsMaterial collidedMaterial = null;
    public Collision collision              = null;
    public IPhysicsObject collidedObject    = null;
    public float collisionDistance          = Float.POSITIVE_INFINITY;
    
    
    @Override
    public String toString() {
        Shape cshape = this.collidedShape;
        PhysicsMaterial material = this.collidedMaterial;
        String shape = (cshape != null) ? cshape.getShapeName() : "null";
        
        String bounciness = "null";
        String friction = "null";
        String density = "null";
        
        if( material != null )
        {
            bounciness = "" + material.getBounciness();
            friction = "" + material.getFriction();
            density = "" + material.getDensity();
        }
        
        return (
            "didCollide         : " + this.didCollide + "\n" +
            "collision          : " + this.collision + "\n" +
            "collidedMesh       : " + this.collidedMesh + "\n" +
            "collidedShape      : " + shape + "\n" +
            "collidedMaterial   : " + this.collidedMaterial + "\n" +
            "   bounciness      : " + bounciness + "\n" +
            "   friction        : " + friction + "\n" +
            "   density         : " + density + "\n" +
            "collisionDistance  : " + this.collisionDistance
        );
    }
}
