package johnengine.basic.game.components.physics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.ITickable;

public class CPhysics implements ITickable {
    
    public static class Force implements ITickable {
        private float peakForce;
        private float peakTime;
        private float currentForce;
        private float currentDuration;
        private float dropOffRate;
        private Vector3f direction;

        public Force(float peakForce, float peakTime, float dropOffRate, Vector3f direction) {
            this.peakForce = peakForce;
            this.peakTime = peakTime;
            this.dropOffRate = dropOffRate;
            this.currentForce = 0.0f;
            this.currentDuration = 0.0f;
            this.direction = direction;
        }

        
        @Override
        public void tick(float deltaTime) {
            this.currentDuration += deltaTime;
            
            if( this.currentDuration > this.peakTime )
            {
                this.currentForce = Math.max(0.0f, this.currentForce - this.dropOffRate);
                this.currentDuration = this.peakTime;
            }
            else
            this.currentForce = this.peakForce * Math.min(1.0f, this.peakTime / this.currentDuration);
            
            this.direction.normalize().mul(this.currentForce);
        }
        
        
        public Vector3f get() {
            return this.direction;
        }
    }
    

    private CTransform target;
    private List<CPhysics.Force> forces;
    
    public CPhysics() {
        this.target = null;
        this.forces = new ArrayList<>();
    }
    
    
    @Override
    public void tick(float deltaTime) {
        if( this.forces.size() == 0 )
        return;
        
        Vector3f totalForce = new Vector3f();
        for( int i = this.forces.size() - 1; i >= 0; i-- )
        {
            CPhysics.Force force = this.forces.get(i);
            force.tick(deltaTime);
            
            totalForce.add(force.get());
            
            if( force.get().length() <= 0.000001f )
            this.forces.remove(i);
        }
        
        this.target.getPosition().shift(totalForce);
    }
    
    public void applyForce(CPhysics.Force force) {
        this.forces.add(force);
    }
    
    public CPhysics.Force createForce() {
        
    }
    
    
    public void setTarget(CTransform target) {
        this.target = target;
    }
}
