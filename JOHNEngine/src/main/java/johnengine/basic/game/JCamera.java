package johnengine.basic.game;

import johnengine.core.IRenderBufferStrategy;

public class JCamera extends AWorldObject implements IControllable {
    
    private CProjection viewProjection;
    private CController controller;

    public JCamera(JWorld world) {
        super(world);
        this.viewProjection = new CProjection();
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
        this.position.get().set(this.position.get().x, this.position.get().y, this.position.get().z- 0.1f);
    }


    @Override
    public void moveBackward() {
        this.position.get().set(this.position.get().x, this.position.get().y, this.position.get().z+ 0.1f);
    }
}
