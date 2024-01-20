package johnengine.basic.game.physics;

import org.joml.Vector3f;

public class Force {
    public static final int APPLY_X = 1;
    public static final int APPLY_Y = 2;
    public static final int APPLY_Z = 4;
    public static final int APPLY_ALL = APPLY_X | APPLY_Y | APPLY_Z;
    
    private Vector3f forceVector;
    private Vector3f direction;
    private float magnitude;
    
    public Force() {
        this.forceVector = new Vector3f(0.0f);
        this.direction = new Vector3f(0.0f);
        this.magnitude = 0.0f;
    }
    
    
    public void update() {
        this.forceVector.normalize(this.direction); // Update direction
        this.magnitude = this.forceVector.length(); // Update magnitude
    }
    
    public void applyImpulse(AImpulse impulse) {
        this.forceVector.add(impulse.getForce().getForceVector());
        this.update();
    }
    
    
    /*********************** SETTERS ***********************/
    
    public void set(Vector3f force) {
        this.forceVector.set(force);
        this.update();
    }
    
    public void setMagnitude(float magnitude) {
        this.forceVector.normalize().mul(magnitude);
        this.update();
    }
    
    public void setDirection(Vector3f direction) {
        this.forceVector.set(direction).mul(this.magnitude);
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
