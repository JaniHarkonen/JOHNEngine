package johnengine.basic.game.physics;

import org.joml.Vector3f;

public class Force {
    private Vector3f forceVector;
    private Vector3f direction;
    private float magnitude;
    
    public Force() {
        this.forceVector = new Vector3f(0.0f);
        this.direction = new Vector3f(0.0f);
        this.magnitude = 0.0f;
    }
    
    
    public void update() {
        this.forceVector.set(this.direction).mul(this.magnitude);
        //this.forceVector.normalize(this.direction); // Update direction
        //this.magnitude = this.forceVector.length(); // Update magnitude
    }
    
    public void applyImpulse(AImpulse impulse) {
        
            // Use forceVector as a temp variable
        this.forceVector.add(impulse.getForce().getForceVector());
        this.setDirectionAndMagnitude(this.forceVector);
        this.update();
    }
    
    private void setDirectionAndMagnitude(Vector3f source) {
        this.setMagnitudeSilent(source.length());
        this.setDirectionSilent(PhysicsUtils.normalizeVector3fSafe(source));
    }
    
    
    /*********************** SETTERS ***********************/
    
    public void set(float xForce, float yForce, float zForce) {
        
            // Use forceVector as a temp variable
        this.forceVector.set(xForce, yForce, zForce);
        this.setDirectionAndMagnitude(new Vector3f(this.forceVector));
        
            // update()-call has been omitted on purpose as set()
            // is effectively the same as update()
    }
    
    public void set(Vector3f forceVector) {
        this.set(forceVector.x, forceVector.y, forceVector.z);
    }
    
    public void setMagnitudeSilent(float magnitude) {
        this.magnitude = magnitude;
    }
    
    public void setDirectionSilent(Vector3f direction) {
        this.direction.set(direction);
    }
    
    public void setMagnitude(float magnitude) {
        this.setMagnitudeSilent(magnitude);
        this.update();
    }
    
    public void setDirection(Vector3f direction) {
        this.setDirectionSilent(direction);
        this.update();
    }
    
    
    
    /*********************** GETTERS ***********************/
    
    public Vector3f getForceVector() {
        return this.forceVector;
    }
    
    public Vector3f getDirection() {
        return this.direction;
    }
    
    public float getMagnitude() {
        return this.magnitude;
    }
}
