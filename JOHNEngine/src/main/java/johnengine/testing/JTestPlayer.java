package johnengine.testing;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.physics.CPhysics3;
import johnengine.basic.game.components.physics.rewrite3.ConstantForce;
import johnengine.basic.game.input.IControllable;

public class JTestPlayer extends AWorldObject implements IControllable {

    private CController controller;
    private CPhysics3 physics;
    private ConstantForce friction;
    private float movementSpeed;
    
    public JTestPlayer(JWorld world) {
        super(world);
        this.controller = null;
        this.movementSpeed = 0.05f;
        this.physics = new CPhysics3();
        this.physics.setTarget(this.transform);
        this.friction = new ConstantForce(0.0f, 0.0f, 0.0f, 0.01f);
        this.physics.applyForce(this.friction);
        //this.physics.applyForce(new ConstantForce(0.0f, 1.0f, 0.0f, 0.01f));
    }

    @Override
    public void tick(float deltaTime) {
        this.controller.tick(deltaTime);
        //DebugUtils.log(this, this.physics.getDirection());
        
        if( this.physics.getNetForce().length() > 0 )
        this.friction.setDirection((new Vector3f(this.physics.getDirection())).negate());
        
        this.physics.tick(deltaTime);
        
        //DebugUtils.log(this, this.physics.getDirection());
        //DebugUtils.log(this, this.getTransform().getPosition().get().x, this.getTransform().getPosition().get().y, this.getTransform().getPosition().get().z);
    }

    
    private void moveTowardsDirection(float dirX, float dirY, float dirZ, float speed) {
        ConstantForce jolt = new ConstantForce(dirX, dirY, dirZ, speed, 0.0f);
        this.physics.applyForce(jolt);
        //CPhysics3.ConstantForce jolt = new CPhysics3.ConstantForce(dirX, dirY, dirZ, speed);
        //this.physics.applyForce(new CPhysics.Force(speed, 0.5f, 0.003f, new Vector3f(dirX, dirY, dirZ)));
        //this.transform.getPosition().shift(dirX * speed, dirY * speed, dirZ * speed);
    }
    
    @Override
    public void moveForward(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getForwardVector(direction);
        this.moveTowardsDirection(direction.x, direction.y, direction.z, this.movementSpeed);
    }

    @Override
    public void moveBackward(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getBackwardVector(direction);
        this.moveTowardsDirection(direction.x, direction.y, direction.z, this.movementSpeed);
    }

    @Override
    public void moveLeft(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getLeftVector(direction);
        this.moveTowardsDirection(direction.x, direction.y, direction.z, this.movementSpeed);
    }

    @Override
    public void moveRight(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getRightVector(direction);
        this.moveTowardsDirection(direction.x, direction.y, direction.z, this.movementSpeed);
    }

    @Override
    public void turn(float deltaX, float deltaY) {
        this.transform.getRotation().rotate(0, deltaX, 0);
        this.transform.getRotation().rotate(deltaY, 0, 0);
    }

    @Override
    public void setController(CController controller) {
        IControllable.super.setController(controller);
        this.controller = controller;
    }

}
