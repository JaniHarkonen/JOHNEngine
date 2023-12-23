package johnengine.basic.game;

import org.joml.Vector3f;

import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.geometry.CProjection;
import johnengine.core.renderer.IRenderStrategy;

public class JCamera extends AWorldObject implements IControllable {
    
    private CProjection viewProjection;
    private CController controller;

    public JCamera(JWorld world) {
        super(world);
        this.viewProjection = new CProjection();
        this.controller = null;
    }

    
    @Override
    public void render(IRenderStrategy renderStrategy) {
        renderStrategy.executeStrategoid(this);
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
        this.rotation.rotateX(angle);
    }
    
    @Override
    public void rotateY(float angle) {
        this.rotation.rotateY(angle);
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
        Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveZ(movementVector);
        this.position.get().add(movementVector.mul(0.05f));
    }

    @Override
    public void moveBackward() {
        Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveZ(movementVector);
        this.position.get().sub(movementVector.mul(0.05f));
    }
    
    @Override
    public void moveLeft() {
        Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveX(movementVector);
        this.position.get().add(movementVector.mul(0.05f));
    }

    @Override
    public void moveRight() {
        Vector3f movementVector = new Vector3f();
        this.rotation.get().positiveX(movementVector);
        this.position.get().sub(movementVector.mul(0.05f));
    }
}
