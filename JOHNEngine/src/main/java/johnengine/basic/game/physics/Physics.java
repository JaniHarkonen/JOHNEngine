package johnengine.basic.game.physics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

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
    
    public void update(float deltaTime, Physics.World physicsWorld) {
        for( IPhysicsObject physicsObject : physicsWorld.getPhysicsObjects() )
        {
                // Static bodies need not be considered
            CPhysics physicsComponent = physicsObject.getPhysics();
            if( physicsComponent.isStatic() )
            return;
            
            this.applyImpulses(physicsObject, deltaTime);
            
            physicsComponent.getCollidingObjects().clear();
            for( IPhysicsObject otherObject : physicsWorld.getPhysicsObjects() )
            {
                CollisionData collisionData = new CollisionData();
                this.calculateCollision(
                    physicsObject,
                    otherObject, 
                    deltaTime,
                    collisionData
                );
                this.resolveCollision(physicsObject, otherObject, collisionData);
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
    
    private void resolveCollision(
        IPhysicsObject target, 
        IPhysicsObject other, 
        CollisionData collisionData
    ) {
        CPhysics physicsComponent = target.getPhysics();
        Force netForce = physicsComponent.getNetForce();
        
        if( collisionData.didCollide )
        {
                // Move to the point of collision
            Vector3f netForceDirection = netForce.getDirection();
            float collisionNear = Math.max(
                0, 
                collisionData.collisionDistance - 
                PhysicsUtils.INTERSECTION_EPSILON
            );
            
            this.shiftObject(
                target,
                0,//netForceDirection.x * collisionNear,
                0,//netForceDirection.y * collisionNear,
                0//netForceDirection.z * collisionNear
            );
            
            //physicsComponent.deleteForce(2);
            //netForce.set(netForce.getForceVector().x, 0, netForce.getForceVector().z);
            physicsComponent.setCollisionBelow(collisionData);
            physicsComponent.getCollidingObjects()
            .put(collisionData.collidedObject, collisionData);
            //physicsComponent.resetGravity();
            
                // Apply a force to the opposite direction depending on 
                // the properties of the physics material of the surface
                // that the physics object collided with
            //physicsComponent.reset();
            //target.getPhysics().applyForce(force);
        }
        else
        {
                // No collision
            this.shiftObject(target, netForce.getForceVector());
            physicsComponent.setCollisionBelow(null);
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
