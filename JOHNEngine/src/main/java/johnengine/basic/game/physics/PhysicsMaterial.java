package johnengine.basic.game.physics;

public class PhysicsMaterial {

    private float bounciness;
    private float friction;
    private float density;
    
    public PhysicsMaterial(float bounciness, float friction, float density) {
        this.bounciness = bounciness;
        this.friction = friction;
        this.density = density;
    }
    
    public PhysicsMaterial() {
        this(0.0f, 0.0f, 0.0f);
    }
    
    
    public void setBounciness(float bounciness) {
        this.bounciness = bounciness;
    }
    
    public void setFriction(float friction) {
        this.friction = friction;
    }
    
    public void setDensity(float density) {
        this.density = density;
    }
    
    public float getBounciness() {
        return this.bounciness;
    }
    
    public float getFriction() {
        return this.friction;
    }
    
    public float getDensity() {
        return this.density;
    }
}
