package johnengine.basic.game.components.physics;

public class ConstantImpulse extends AImpulse {

    public ConstantImpulse(float constant) {
        super();
        this.magnitude = constant;
    }


    @Override
    public void tick(float deltaTime) { }
    
    @Override
    public float getMagnitude() {
        return this.magnitude;
    }
    
    @Override
    public boolean hasExpired() {
        return false;   // cannot expire
    }
}
