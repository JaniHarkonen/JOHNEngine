package johnengine.basic.game.rewrite;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.CController;
import johnengine.basic.game.CRotation;
import johnengine.basic.game.IControllable;
import johnengine.basic.game.JWorld;
import johnengine.core.IRenderBufferStrategy;

public class JCamera extends AWorldObject implements IControllable {
    
    private CProjection viewProjection;
    private CRotation rotation;
    private CController controller;

    public JCamera(JWorld world) {
        super(world);
        this.viewProjection = new CProjection();
        this.rotation = new CRotation();
        this.controller = null;
    }

    
    @Override
    public void render(IRenderBufferStrategy renderBufferStrategy) {
        renderBufferStrategy.executeStrategoid(this);
    }

    @Override
    public void tick(float deltaTime) {
        if( this.controller != null )
        this.controller.tick(deltaTime);
    }
    
    
    public CProjection getProjection() {
        return this.viewProjection;
    }
    
    public CRotation getRotation() {
        return this.rotation;
    }

    @Override
    public void rotateX(float angle) {
        this.rotation.rotate(0, angle);
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
        this.position.get().set(this.position.get().x, this.position.get().y, this.position.get().z- 0.1f);
    }


    @Override
    public void moveBackward() {
        this.position.get().set(this.position.get().x, this.position.get().y, this.position.get().z+ 0.1f);
    }
}
