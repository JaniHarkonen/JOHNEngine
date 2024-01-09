package johnengine.basic.game;

import org.joml.Vector3f;

import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.geometry.CProjection;
import johnengine.basic.game.input.IControllable;

public class JCamera extends AWorldObject implements IControllable {
    
    private CProjection viewProjection;
    private CController DEBUGCONTROLLER;

    public JCamera(JWorld world) {
        super(world);
        this.viewProjection = new CProjection();
        this.DEBUGCONTROLLER = null;
    }


    @Override
    public void tick(float deltaTime) {
        if( this.DEBUGCONTROLLER != null )
        this.DEBUGCONTROLLER.tick(deltaTime);
    }
    
    
    public CProjection getProjection() {
        return this.viewProjection;
    }


    @Override
    public void moveForward(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getForwardVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
    }


    @Override
    public void moveBackward(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getBackwardVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
    }


    @Override
    public void moveLeft(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getLeftVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
    }


    @Override
    public void moveRight(float intensity) {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getRightVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
    }


    @Override
    public void turn(float deltaX, float deltaY) {
        this.transform.getRotation().rotate(0, deltaX, 0);
        this.transform.getRotation().rotate(deltaY, 0, 0);
    }


    @Override
    public void setController(CController controller) {
        this.DEBUGCONTROLLER = controller;
        this.DEBUGCONTROLLER.setTarget(this);
    }
}
