package johnengine.basic.game.physics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Math;
import org.joml.Vector3f;

import johnengine.basic.game.components.CPhysics;
import johnengine.basic.game.physics.collision.Collision;
import johnengine.basic.game.physics.collision.CollisionData;

public class Physics {
    
    /******************** World-class ********************/
    public static class World {
        private final List<IPhysicsObject> physicsObjects;
        
        public World() {
            this.physicsObjects = new ArrayList<>();
        }
        
        
        public void addObject(IPhysicsObject physicsObject) {
            this.physicsObjects.add(physicsObject);
        }
        
        public List<IPhysicsObject> getPhysicsObjects() {
            return this.physicsObjects;
        }
    }
    
    
    /******************** Physics-class ********************/

    public static final float INTERSECTION_EPSILON = 0.00001f;
    
    public static float calculateNormalizationScalar(float x, float y, float z) {
        return Math.invsqrt(Math.fma(x, x, Math.fma(y, y, z * z)));
    }
    
    public static float calculateNormalizationScalar(Vector3f v) {
        return calculateNormalizationScalar(v.x, v.y, v.z);
    }
    
    
    public void update(float deltaTime, Physics.World physicsWorld) {
        for( IPhysicsObject physicsObject : physicsWorld.getPhysicsObjects() )
        {
                // Static bodies need not be considered
            if( physicsObject.getPhysics().isStatic() )
            return;
            
            this.applyImpulses(physicsObject, deltaTime);
            
            for( IPhysicsObject otherObject : physicsWorld.getPhysicsObjects() )
            {
                CollisionData collision = new CollisionData();
                this.calculateCollision(
                    physicsObject,
                    otherObject, 
                    deltaTime,
                    collision
                );
                this.resolveCollision(physicsObject, collision);
            }
        }
    }
    
    private void applyImpulses(IPhysicsObject target, float deltaTime) {
        target.getPhysics().tick(deltaTime);
    }
    
    private void calculateCollision(
        IPhysicsObject target,
        IPhysicsObject other, 
        float deltaTime,
        CollisionData result
    ) {
        CPhysics physicsComponent = target.getPhysics();
        Collision targetCollision = physicsComponent.getCollision();
        
        targetCollision.checkCollision(
            target.getTransform(), 
            physicsComponent.getNetForce().getForceVector(), 
            other.getTransform(), 
            other.getPhysics().getCollision(), 
            true,
            result
        );
    }
    
    private void resolveCollision(IPhysicsObject target, CollisionData collisionData) {
        if( collisionData != null )
        {
                // Move to the point of collision
            float collisionNear = collisionData.collisionDistanceNear;
            Vector3f netForceDirection = target.getPhysics().getNetForce().getDirection();
            
            this.shiftObject(
                target,
                netForceDirection.x * collisionNear,
                netForceDirection.y * collisionNear,
                netForceDirection.z * collisionNear
            );
            
                // Apply a force to the opposite direction depending on 
                // the properties of the physics material of the surface
                // that the physics object collided with
            //target.getPhysics().applyForce(force);
        }
        else
        {
                // No collision, keep moving according to net force
            Vector3f netForceVector = target.getPhysics().getNetForce().getForceVector();
            this.shiftObject(target, netForceVector);
        }
    }
    
        // Helper to shift physics objects around (avoids stringing getters together)
    private void shiftObject(IPhysicsObject target, float shiftX, float shiftY, float shiftZ) {
        target.getTransform().getPosition().shift(shiftX, shiftY, shiftZ);
    }

        // Helper to shift physics objects around (avoids stringing getters together)
    private void shiftObject(IPhysicsObject target, Vector3f shift) {
        this.shiftObject(target, shift.x, shift.y, shift.z);
    }
}
