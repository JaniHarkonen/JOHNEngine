package johnengine.basic.game.components.physics;

public class LogImpulse extends AImpulse {
    private float peakForce;
    private float currentForce;
    private float duration;
    private float currentDuration;

    public LogImpulse(float peakForce, float duration) {
        this.peakForce = peakForce;
        this.currentForce = 0.0f;
        this.duration = duration;   
        this.currentDuration = 0.0f;
    }

    
    @Override
    public void tick(float deltaTime) {
        this.currentDuration = Math.min(this.duration, this.currentDuration + deltaTime);
        this.currentForce = this.currentDuration / this.duration * this.peakForce;
    }
}
