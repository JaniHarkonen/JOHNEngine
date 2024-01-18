package johnengine.basic.game.components.physics.rewrite3;

public class ConstantForce extends AForce {
    private float duration;
    private float exertedFor;
    
    public ConstantForce(float directionX, float directionY, float directionZ, float magnitude, float duration) {
        super(directionX, directionY, directionZ, magnitude);
        this.duration = duration;
        this.exertedFor = 0.0f;
        this.updateForce();
    }
    
    public ConstantForce(float directionX, float directionY, float directionZ, float magnitude) {
        this(directionX, directionY, directionZ, magnitude, Float.POSITIVE_INFINITY);
    }

    
    @Override
    public void tick(float deltaTime) {
        this.exertedFor += deltaTime;
        
        if( this.exertedFor >= this.duration )
        this.hasExpired = true;
        
        this.updateForce();
    }
}
