package johnengine.basic.game.components.physics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.physics.rewrite3.AForce;
import johnengine.basic.game.physics.Collision;

public class CPhysics3 {    
    private List<AForce> forces;
    private Vector3f netForce;
    private Vector3f direction;
    private AWorldObject target;
    private boolean isStatic;
    private Collision collision;
    
    public CPhysics3() {
        this.forces = new ArrayList<>();
        this.netForce = new Vector3f(0.0f);
        this.direction = new Vector3f(0.0f);
        this.target = null;
        this.isStatic = false;
        this.collision = null;
    }
    
    
    public void update(float deltaTime, JWorld world) {
        if( this.isStatic )
        return;
        
        for( int i = this.forces.size() - 1; i >= 0; i-- )
        {
            AForce force = this.forces.get(i);
            this.netForce.add(force.getForce());
            force.tick(deltaTime);
            
            if( force.hasExpired() )
            this.forces.remove(i);
        }
        
        
        
        this.target.getTransform().getPosition().shift(this.netForce);
    }
    
    public void applyForce(AForce force) {
        this.forces.add(force);
    }
    
    
    public void setTarget(AWorldObject target) {
        this.target = target;
    }
    
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
    
    public void setCollision(Collision collision) {
        this.collision = collision;
    }
    
    public float getVelocity() {
        return this.netForce.length();
    }
    
    public Vector3f getNetForce() {
        return this.netForce;
    }
    
    public Vector3f getDirection() {
        this.netForce.normalize(this.direction);
        return this.direction;
    }
    
    public boolean isStatic() {
        return this.isStatic;
    }
    
    public Collision getCollision() {
        return this.collision;
    }
}
