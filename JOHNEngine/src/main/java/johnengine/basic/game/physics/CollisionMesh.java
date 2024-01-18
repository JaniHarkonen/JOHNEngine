package johnengine.basic.game.physics;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.shapes.Shape;

public class CollisionMesh {
    private Shape[] shapes;
    private PhysicsMaterial physicsMaterial;
    
    public CollisionMesh(PhysicsMaterial physicsMaterial) {
        this.physicsMaterial = physicsMaterial;
        this.shapes = new Shape[0];
    }
    
    
    public CollisionData checkCollision(CTransform myTransform, Vector3f velocity, CTransform otherTransform, CollisionMesh other) {
        for( Shape myShape : this.shapes )
        {
            for( Shape otherShape : other.shapes )
            {
                CollisionData result = myShape.checkCollision(myTransform, velocity, otherTransform, otherShape);
                if( result != null )
                {
                    result.collidedShape = otherShape;
                    result.collidedMaterial = this.physicsMaterial;
                    result.collidedMesh = this;
                    return result;
                }
            }
        }
        
        return null;
    }
    
    
    public void setCollisionShapes(Shape... shapes) {
        this.shapes = shapes;
    }
    
    public void setPhysicsMaterial(PhysicsMaterial physicsMaterial) {
        this.physicsMaterial = physicsMaterial;
    }
    
    
    public Shape[] getCollisionShapes() {
        return this.shapes;
    }
    
    public PhysicsMaterial getPhysicsMaterial() {
        return this.physicsMaterial;
    }
}
