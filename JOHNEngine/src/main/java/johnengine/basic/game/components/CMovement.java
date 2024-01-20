package johnengine.basic.game.components;

import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.core.ITickable;

public class CMovement implements ITickable {
    
    public static final float DEFAULT_SPEED_UP_TIME = 1.0f;
    public static final float DEFAULT_STOP_TIME = 0.5f;

    private IPhysicsObject target;
    private float xMovementIntensity;
    private float zMovementIntensity;
    private float xMovementTargetIntensity;
    private float zMovementTargetIntensity;
    private float speedUpTime;
    private float stopTime;
    
    public CMovement(IPhysicsObject target) {
        this.target = target;
        this.xMovementIntensity = 0.0f;
        this.zMovementIntensity = 0.0f;
        this.xMovementTargetIntensity = 0.0f;
        this.zMovementTargetIntensity = 0.0f;
        this.speedUpTime = DEFAULT_SPEED_UP_TIME;
        this.stopTime = DEFAULT_STOP_TIME;
    }
    
    public CMovement() {
        this(null);
    }
    
    
    @Override
    public void tick(float deltaTime) {
        if( 
            this.xMovementIntensity < this.xMovementTargetIntensity ||
            this.zMovementIntensity < this.zMovementTargetIntensity
        )
        {
            float a = this.xMovementIntensity;
            float b = this.xMovementTargetIntensity;
            float t = this.speedUpTime;
            this.xMovementIntensity = a + (b - a) * t;
            
            
        }
    }
    
    public void moveX(float xMovementIntensity) {
        this.xMovementTargetIntensity = xMovementIntensity;
    }
    
    public void moveZ(float zMovementIntensity) {
        this.zMovementTargetIntensity = zMovementIntensity;
    }
    
    
    public void setTarget(IPhysicsObject target) {
        this.target = target;
    }
}
