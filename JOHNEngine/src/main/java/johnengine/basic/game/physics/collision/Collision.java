package johnengine.basic.game.physics.collision;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class Collision {

    private CollisionMesh[] collisionMeshes;
    private Shape lodShape;
    private float weight;
    
    public Collision(CollisionMesh... collisionMeshes) {
        this.collisionMeshes = collisionMeshes;
        this.weight = 0.0f;
        this.lodShape = new Shape("box");
    }
    
    public Collision() {
        this(new CollisionMesh[0]);
    }
    
    
    public boolean checkCollision(
        CTransform myTransform, 
        Vector3f myVelocity, 
        CTransform otherTransform, 
        Collision otherCollision, 
        boolean ignoreSelf,
        CollisionData result
    ) {
        
            // Perform an low resolution collision check before iterating over 
            // the collision meshes, also ignore self if needed
        if( ignoreSelf && myTransform == otherTransform )
        return false;
        
        if( !this.lodShape.checkCollision(
            myTransform, 
            myVelocity, 
            otherTransform, 
            otherCollision.lodShape,
            result
        ) )
        return false;
        
            // Perform a more detailed collision check
        for( CollisionMesh myMesh : this.collisionMeshes )
        {
            for( CollisionMesh otherMesh : otherCollision.collisionMeshes )
            {
                if( myMesh.checkCollision(myTransform, myVelocity, otherTransform, otherMesh, result) )
                return true;
            }
        }
        
        return false;
    }
    
    
    public void setCollisionMeshes(CollisionMesh[] collisionMeshes) {
        this.collisionMeshes = collisionMeshes;
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    public void setLodShape(Shape lodShape) {
        this.lodShape = lodShape;
    }
    
    
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
