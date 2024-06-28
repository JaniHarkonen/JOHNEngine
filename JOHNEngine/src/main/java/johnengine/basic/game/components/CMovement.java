package johnengine.basic.game.components;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.Force;
import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.basic.game.physics.impulses.ConstantImpulse;
import johnengine.core.ITickable;

public class CMovement implements ITickable {
    public static final float DEFAULT_SPEED_UP_TIME = 1.0f;
    public static final float DEFAULT_STOP_TIME = 0.5f;

    private IPhysicsObject target;
    private float speedUpTime;
    private float stopTime;
    private float xMovementIntensity;
    private float zMovementIntensity;
    private float xMovementTargetIntensity;
    private float zMovementTargetIntensity;
    private float xMovementDelta;
    private float zMovementDelta;
    private ConstantImpulse xImpulse;
    private ConstantImpulse zImpulse;
    
    public CMovement(IPhysicsObject target) {
        this.target = target;
        this.speedUpTime = DEFAULT_SPEED_UP_TIME;
        this.stopTime = DEFAULT_STOP_TIME;
        this.xMovementIntensity = 0.0f;
        this.zMovementIntensity = 0.0f;
        this.xMovementTargetIntensity = 0.0f;
        this.zMovementTargetIntensity = 0.0f;
        this.xImpulse = new ConstantImpulse();
        this.zImpulse = new ConstantImpulse();
        
        this.target.getPhysics().applyImpulse(this.xImpulse);
        this.target.getPhysics().applyImpulse(this.zImpulse);
        //this.xMovementDelta = ;
    }
    
    public CMovement() {
        this(null);
    }
    
    
    @Override
    public void tick(float deltaTime) {
        float speedMultiplier = 0.4f;
        
        CTransform targetTransform = this.target.getTransform();
        CTransform.Rotation targetRotation = targetTransform.getRotation();
        targetTransform.update();
        
        Force force = this.xImpulse.getForce();
        //force.setDirection(targetRotation.getRightVector(force.getDirection()));
        force.setMagnitude(this.xMovementIntensity * speedMultiplier);
        
        force = this.zImpulse.getForce();
        //force.setDirection(targetRotation.getForwardVector(force.getDirection()));
        force.setMagnitude(this.zMovementIntensity * speedMultiplier);
        
        this.xMovementIntensity = 0.0f;
        this.zMovementIntensity = 0.0f;
    }
    
    public void moveX(float xMovementIntensity) {
        this.xMovementIntensity = xMovementIntensity;
        float speedMultiplier = 0.4f;
        
        CTransform targetTransform = this.target.getTransform();
        CTransform.Rotation targetRotation = targetTransform.getRotation();
        targetTransform.update();
        
        Force force = this.xImpulse.getForce();
        force.setDirection(targetRotation.getRightVector(force.getDirection()));
        force.setMagnitude(this.xMovementIntensity * speedMultiplier);
        //this.xMovementIntensity = xMovementIntensity;
        //this.xMovementTargetIntensity = xMovementIntensity;
    }
    
    public void moveZ(float zMovementIntensity) {
        this.zMovementIntensity = zMovementIntensity;
        float speedMultiplier = 0.4f;
        
        CTransform targetTransform = this.target.getTransform();
        CTransform.Rotation targetRotation = targetTransform.getRotation();
        targetTransform.update();
        
        Force force = this.zImpulse.getForce();
        force.setDirection(targetRotation.getForwardVector(force.getDirection()));
        force.setMagnitude(this.zMovementIntensity * speedMultiplier);
        //this.zMovementTargetIntensity = zMovementIntensity;
    }
    
    
    public void setTarget(IPhysicsObject target) {
        this.target = target;
    }
}
