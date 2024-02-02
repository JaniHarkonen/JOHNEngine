package johnengine.basic.game.physics.collision;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.PhysicsMaterial;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class CollisionMesh {
    private Shape[] shapes;
    private PhysicsMaterial physicsMaterial;
    
    public CollisionMesh(PhysicsMaterial physicsMaterial) {
        this.physicsMaterial = physicsMaterial;
        this.shapes = new Shape[0];
    }
    
    
    public boolean checkCollision(
        CTransform myTransform, 
        Vector3f velocity, 
        CTransform otherTransform, 
        CollisionMesh other,
        CollisionData result
    ) {
        for( Shape myShape : this.shapes )
        {
            for( Shape otherShape : other.shapes )
            {
                if( !myShape.checkCollision(myTransform, velocity, otherTransform, otherShape, result) )
                continue;
                
                result.collidedShape = otherShape;
                result.collidedMaterial = other.physicsMaterial;
                result.collidedMesh = other;
                return true;
            }
        }
        
        return false;
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
