package johnengine.basic.game.physics.collision;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class Collision {

    private IPhysicsObject target;
    private CollisionMesh[] collisionMeshes;
    private Shape lodShape;
    private float weight;
    
    public Collision(IPhysicsObject target, CollisionMesh... collisionMeshes) {
        this.target = target;
        this.collisionMeshes = collisionMeshes;
        this.weight = 0.0f;
        this.lodShape = new Shape("sphere");
    }
    
    public Collision(IPhysicsObject target) {
        this(target, new CollisionMesh[0]);
    }
    
    
    public boolean checkCollision(
        CTransform myTransform, 
        Vector3f myVelocity, 
        CTransform otherTransform, 
        Collision otherCollision, 
        boolean ignoreSelf,
        CollisionData result
    ) {
            // Perform a crude collision check before iterating over 
            // the collision meshes, also ignore self if needed
        if( ignoreSelf && myTransform == otherTransform )
        return false;
        
        if( !this.lodShape.checkCollision
            (
                myTransform, 
                myVelocity, 
                otherTransform, 
                otherCollision.lodShape,
                result
            )
        ) return false;
        
            // Perform a more comprehensive collision check
        for( CollisionMesh myMesh : this.collisionMeshes )
        {
            for( CollisionMesh otherMesh : otherCollision.collisionMeshes )
            {
                myMesh.checkCollision(
                    myTransform, 
                    myVelocity, 
                    otherTransform, 
                    otherMesh, 
                    result
                );
                
                if( !result.didCollide )
                continue;
                
                result.collision = otherCollision;
                result.collidedObject = otherCollision.target;
                return true;
            }
        }
        
        return false;
    }
    
    
    /*********************** SETTERS ***********************/
    
    public void setCollisionMeshes(CollisionMesh... collisionMeshes) {
        this.collisionMeshes = collisionMeshes;
    }
    
    public void setCollisionMesh(CollisionMesh collisionMesh) {
        this.setCollisionMeshes(collisionMesh);
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    public void setLodShape(Shape lodShape) {
        this.lodShape = lodShape;
    }
    
    
    /*********************** GETTERS ***********************/
    
    public CollisionMesh[] getCollisionMeshes() {
        return this.collisionMeshes;
    }
    
    public float getWeight() {
        return this.weight;
    }
    
    public Shape getLodShape() {
        return this.lodShape;
    }
}
