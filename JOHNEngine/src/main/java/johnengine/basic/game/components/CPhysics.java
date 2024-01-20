package johnengine.basic.game.components;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import johnengine.basic.game.physics.AImpulse;
import johnengine.basic.game.physics.Force;
import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.basic.game.physics.collision.Collision;
import johnengine.core.ITickable;

public class CPhysics implements ITickable {
    private final IPhysicsObject target;
    
    private Collision collision;
    private List<AImpulse> impulses;
    private Force netForce;
    private boolean isStatic;
    
    public CPhysics(IPhysicsObject target) {
        this.target = target;
        this.collision = null;
        this.impulses = new ArrayList<>();
        this.netForce = new Force();
        this.isStatic = true;
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
        
            // If the sum of the impulses points to the same direction as the 
            // current net force, the impulses will BECOME THE NEW net force, 
            // as long as the sum of the impulses is greater than the current 
            // net force. If the sum of the impulses points to the opposite 
            // direction, it will be ADDED to the current net force.
        Vector3f netForceVector = this.netForce.getForceVector();
        netForceVector.x = this.setOrAddImpulses(netForceVector.x, impulseTotal.x);
        netForceVector.y = this.setOrAddImpulses(netForceVector.y, impulseTotal.y);
        netForceVector.z = this.setOrAddImpulses(netForceVector.z, impulseTotal.z);
        
        this.netForce.update();
    }
    
    private float setOrAddImpulses(float netForceComponent, float impulseTotalComponent) {
        if( Math.signum(netForceComponent) != Math.signum(impulseTotalComponent) )
        return netForceComponent + impulseTotalComponent;
        
        if( Math.abs(impulseTotalComponent) > Math.abs(netForceComponent) )
        return impulseTotalComponent;
        
        return netForceComponent;
    }   
    
    public void applyImpulse(AImpulse force) {
        this.impulses.add(force);
    }
    
    public AImpulse deleteForce(int forceIndex) {
        return this.impulses.remove(forceIndex);
    }
    
    
    /********************* GETTERS *********************/
    
    public IPhysicsObject getTarget() {
        return this.target;
    }
    
    public Collision getCollision() {
        return this.collision;
    }
    
    public List<AImpulse> getImpulses() {
        return this.impulses;
    }
    
    public Force getNetForce() {
        return this.netForce;
    }
    
    public boolean isStatic() {
        return this.isStatic;
    }
}
