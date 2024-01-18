package johnengine.basic.game.components.physics;

public class ConstantExpiringImpulse extends AImpulse {
    private float duration;
    private float exertedFor;

    public ConstantExpiringImpulse(float constant, float duration) {
        this.magnitude = constant;
        this.duration = duration;
        this.exertedFor = 0.0f;
    }


    @Override
    public void tick(float deltaTime) {
        this.exertedFor += deltaTime;
        this.expire(this.exertedFor, this.duration);
    }
}
