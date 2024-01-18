package johnengine.basic.game.components.physics.rewrite3;

import org.joml.Vector3f;

import johnengine.core.ITickable;

public abstract class AForce implements ITickable {
    protected Vector3f direction;
    protected float magnitude;
    protected Vector3f force;
    protected boolean hasExpired;
    
    public AForce(float directionX, float directionY, float directionZ, float magnitude) {
        this.direction = new Vector3f(directionX, directionY, directionZ);
        this.magnitude = magnitude;
        this.hasExpired = false;
        this.force = new Vector3f(0.0f);
    }
    
    public AForce(Vector3f direction, float magnitude) {
        this(direction.x, direction.y, direction.z, magnitude);
    }
    
    
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
    
    public float getMagnitude() {
        return this.magnitude;
    }
    
    public Vector3f getDirection() {
        return this.direction;
    }
    
    public Vector3f getForce() {
        return this.force;
    }
    
    public boolean hasExpired() {
        return this.hasExpired;
    }
    
    protected void updateForce() {
        this.direction.mul(this.magnitude, this.force);
    }
}
