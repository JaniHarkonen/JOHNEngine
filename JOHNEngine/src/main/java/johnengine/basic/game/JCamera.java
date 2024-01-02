package johnengine.basic.game;

import org.joml.Vector3f;

import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.geometry.CProjection;

public class JCamera extends AWorldObject implements IControllable {
    
    private CProjection viewProjection;
    private CController controller;

    public JCamera(JWorld world) {
        super(world);
        this.viewProjection = new CProjection();
        this.controller = null;
    }


    @Override
    public void tick(float deltaTime) {
        if( this.controller != null )
        this.controller.tick(deltaTime);
    }
    
    
    public CProjection getProjection() {
        return this.viewProjection;
    }
    
    
    @Override
    public void rotateX(float angle) {
        this.transform.getRotation().rotate(angle, 0, 0);
        //this.transform.rotate(new Vector3f(angle, 0.0f, 0.0f));
        //this.rotation.rotateX(angle);
    }
    
    @Override
    public void rotateY(float angle) {
        this.transform.getRotation().rotate(0, angle, 0);
        //this.transform.rotate(new Vector3f(0.0f, angle, 0.0f));
        //this.rotation.rotateY(angle);
    }

    
    @Override
    public CController getController() {
        return this.controller;
    }

    @Override
    public void setController(CController controller) {
        this.controller = controller;
    }


    @Override
    public void moveForward() {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getForwardVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
        //this.transform.getForwardVector(direction);
        //this.transform.shift(direction.mul(0.05f));
        /*
        Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveZ(movementVector);
        this.position.shift(movementVector.mul(0.05f));
        */
    }

    @Override
    public void moveBackward() {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getBackwardVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
        /*Vector3f direction = new Vector3f();
        this.transform.getBackwardVector(direction);
        this.transform.shift(direction.mul(0.05f));*/
        //Vector3f shift = new Vector3f(0.05f);
        //this.transform.shift(shift.rotate(this.transform.getRotation()));
        /*Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveZ(movementVector);
        this.position.shift(movementVector.negate().mul(0.05f));*/
    }
    
    @Override
    public void moveLeft() {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getLeftVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
        /*Vector3f direction = new Vector3f();
        this.transform.getLeftVector(direction);
        this.transform.shift(direction.mul(0.05f));*/
        /*Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveX(movementVector);
        this.position.shift(movementVector.mul(0.05f));*/
    }

    @Override
    public void moveRight() {
        Vector3f direction = new Vector3f();
        this.transform.getRotation().getRightVector(direction);
        this.transform.getPosition().shift(direction.mul(0.05f));
        /*Vector3f direction = new Vector3f();
        this.transform.getRightVector(direction);
        this.transform.shift(direction.mul(0.05f));*/
        /*Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveX(movementVector);
        this.position.shift(movementVector.negate().mul(0.05f));*/
    }
}
