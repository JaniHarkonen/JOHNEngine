package johnengine.basic.game.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import johnengine.basic.game.physics.collision.Collision;
import johnengine.basic.game.physics.collision.CollisionData;
import johnengine.basic.game.physics.impulses.ConstantImpulse;
import johnengine.core.ITickable;

public class CPhysics implements ITickable {
    private final IPhysicsObject target;
    
    private List<AImpulse> impulses;
    private Force netForce;
    private AImpulse gravity;
    private CollisionData below;
    private Collision collision;
    private Map<IPhysicsObject, CollisionData> collidingObjects;
    private boolean isStatic;
    
    public CPhysics(IPhysicsObject target) {
        this.target = target;
        this.impulses = new ArrayList<>();
        this.netForce = new Force();
        this.below = null;
        this.collision = new Collision(this.target);
        this.collidingObjects = new HashMap<>();
        this.isStatic = true;
        
        this.gravity = new ConstantImpulse();
        this.gravity.getForce().set(0, 0.05f, 0);
        //this.applyImpulse(this.gravity);
    }
    
    
    @Override
    public void tick(float deltaTime) {
        
            // Calculate and update the net force applied to the object
            // by totaling all the applied impulses. Impulses will also
            // be ticked, and expired ones will be removed.
        Vector3f impulseTotal = new Vector3f(0.0f);
        int begin = this.impulses.size() - 1;
        for( int i = begin; i >= 0; i-- )
        {
            AImpulse impulse = this.impulses.get(i);
            impulseTotal.add(impulse.getForce().getForceVector());
            impulse.tick(deltaTime);
            
                // Expired impulse -> remove
            if( impulse.hasExpired() )
            this.impulses.remove(i);
        }
        
            // The sum of the impulses is now the new net force goal,
            // shift the net force toward the goal by taking into account
            // the magnitude of the force and the friction of the 
            // colliding physics materials
        Vector3f netForceVector = this.netForce.getForceVector();
        Vector3f shiftMagnitude = impulseTotal;
        
        if( netForceVector.length() > impulseTotal.length() )
        shiftMagnitude = netForceVector;
        
        float friction = 1.0f;
        
        if( this.below != null )
        friction = this.below.collidedMaterial.getFriction();
        
        float xShiftMagnitude = shiftMagnitude.x * friction;
        float yShiftMagnitude = shiftMagnitude.y * friction;
        float zShiftMagnitude = shiftMagnitude.z * friction;
        
        float xNewNetForce = PhysicsUtils.shiftf(
            netForceVector.x, 
            impulseTotal.x, 
            xShiftMagnitude * deltaTime
        );
        
        float yNewNetForce = PhysicsUtils.shiftf(
            netForceVector.y, 
            impulseTotal.y, 
            yShiftMagnitude * deltaTime
        );
        
        float zNewNetForce = PhysicsUtils.shiftf(
            netForceVector.z, 
            impulseTotal.z, 
            zShiftMagnitude * deltaTime
        );

        Force gravityForce = this.gravity.getForce();
        
        if( this.below == null )
        gravityForce.setMagnitude(gravityForce.getMagnitude() + 0.01f);
        else
        {
                // When standing on a surface, subtract the surface normal
                // (opposite of the direction of the gravity) from the net
                // force
            Vector3f gravityDirection = this.gravity.getForce().getDirection();
            xNewNetForce -= gravityDirection.x * xNewNetForce;
            yNewNetForce -= gravityDirection.y * yNewNetForce;
            zNewNetForce -= gravityDirection.z * zNewNetForce;
            this.gravity.getForce().setMagnitude(0.0f);
        }
        
        this.netForce.set(xNewNetForce, yNewNetForce, zNewNetForce);
    }
    
    public void applyImpulse(AImpulse force) {
        this.impulses.add(force);
    }
    
    public AImpulse deleteForce(int forceIndex) {
        if( forceIndex < 0 || forceIndex >= this.impulses.size() )
        return null;
        
        return this.impulses.remove(forceIndex);
    }
    
    
    /********************* SETTERS *********************/
    
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
    
    void setCollisionBelow(CollisionData below) {
        this.below = below;
    }
    
    
    /********************* GETTERS *********************/
    
    public IPhysicsObject getTarget() {
        return this.target;
    }
    
    public List<AImpulse> getImpulses() {
        return this.impulses;
    }
    
    public Force getNetForce() {
        return this.netForce;
    }
    
    public CollisionData getCollisionBelow() {
        return this.below;
    }
    
    public Collision getCollision() {
        return this.collision;
    }
    
    public boolean isStatic() {
        return this.isStatic;
    }
    
    public CollisionData checkCollision(IPhysicsObject collidingObject) {
        return this.collidingObjects.get(collidingObject);
    }
    
    public boolean isColliding(IPhysicsObject collidingObject) {
        return (checkCollision(collidingObject) != null);
    }
    
    Map<IPhysicsObject, CollisionData> getCollidingObjects() {
        return this.collidingObjects;
    }
}
