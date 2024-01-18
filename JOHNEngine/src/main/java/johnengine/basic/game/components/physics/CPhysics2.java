package johnengine.basic.game.components.physics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.ITickable;

public class CPhysics2 implements ITickable {
    
    public static class Force implements ITickable {
        private float magnitude;
        private Vector3f direction;
        private Vector3f force;
        private List<AImpulse> impulses;

        public Force(Vector3f direction) {
            this.magnitude = 0.0f;
            this.impulses = new ArrayList<>();
            this.direction = (new Vector3f(direction)).normalize();
            this.force = new Vector3f(this.direction);
        }


        @Override
        public void tick(float deltaTime) {
            for( int i = this.impulses.size() - 1; i >= 0; i-- )
            {
                AImpulse impulse = this.impulses.get(i);
                this.magnitude += impulse.getMagnitude();
                impulse.tick(deltaTime);
                
                if( impulse.hasExpired() )
                this.impulses.remove(i);
            }
        }

        public void applyImpulse(AImpulse impulse) {
            this.impulses.add(impulse);
        }


        public Vector3f getForceVector() {
            this.force
            .set(this.direction)
            .mul(this.magnitude);

            return this.force;
        }
    }
    
    
    /********************* CPhysics-class *********************/
    
    private List<Force> forces;
    private CTransform target;

    public CPhysics2() {
        this.forces = new ArrayList<>();
        this.target = null;
    }


    @Override
    public void tick(float deltaTime) {
        for( Force force : this.forces )
        target.getPosition().shift(force.getForceVector());
    }

    public void registerForce(Force force) {
        this.forces.add(force);
    }


    public void setTarget(CTransform target) {
        this.target = target;
    }
}
