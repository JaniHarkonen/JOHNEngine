package johnengine.basic.game.physics;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.shapes.Shape;

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
    
    
    public CollisionData checkCollision(CTransform myTransform, Vector3f velocity, CTransform otherTransform, Collision other) {
        
            // Perform an low resolution collision check before iterating over 
            // the collision meshes
        CollisionData result = this.lodShape.checkCollision(
            myTransform, 
            velocity, 
            otherTransform, 
            other.lodShape
        );
        
        if( result == null )
        return null;
        
            // Perform a more detailed collision check
        for( CollisionMesh myMesh : this.collisionMeshes )
        {
            for( CollisionMesh otherMesh : other.collisionMeshes )
            {
                result = myMesh.checkCollision(myTransform, velocity, otherTransform, otherMesh);
                
                if( result != null )
                return result;
            }
        }
        
        return null;
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
