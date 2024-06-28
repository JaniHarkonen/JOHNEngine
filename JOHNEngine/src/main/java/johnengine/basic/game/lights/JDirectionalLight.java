package johnengine.basic.game.lights;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.IWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.IControllable;
import johnengine.basic.game.input.actions.ACTMoveLeft;
import johnengine.basic.game.input.actions.ACTMoveRight;

public class JDirectionalLight extends AWorldObject implements IControllable {

    public static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final Vector3f DEFAULT_DIRECTION = new Vector3f(0.0f, -1.0f, 0.0f);
    public static final float DEFAULT_INTENSITY = 0.1f;
    
    private Vector3f color;
    private Vector3f direction;
    private float intensity;
    private CController DEBUGcontroller;
    
    public JDirectionalLight(IWorld world) {
        super(world);
        this.color = DEFAULT_COLOR;
        this.direction = DEFAULT_DIRECTION;
        this.intensity = DEFAULT_INTENSITY;
    }
    
    
    public void setColor(Vector3f color) {
        this.color = color;
    }
    
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
    
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    

    public Vector3f getColor() {
        return this.color;
    }
    
    public Vector3f getDirection() {
        return this.direction;
    }
    
    public float getIntensity() {
        return this.intensity;
    }

    @Override
    public void tick(float deltaTime) {
        this.DEBUGcontroller.tick(deltaTime);
    }


    @Override
    public void control(AControllerAction action) {
        float sens = 0.005f;
        switch( action.action )
        {
            case MOVE_LEFT: {
                //this.direction.add(/*((ACTMoveLeft) action).intensity * sens*/0,((ACTMoveLeft) action).intensity * sens,/*((ACTMoveLeft) action).intensity * sens*/0);
                this.direction.rotateX(((ACTMoveLeft) action).intensity * sens);
            } break;
            
            case MOVE_RIGHT: {
                this.direction.add(((ACTMoveRight) action).intensity * sens,((ACTMoveRight) action).intensity * sens,((ACTMoveRight) action).intensity * sens);
            } break;
        }
    }
    
    @Override
    public void setController(CController controller) {
        IControllable.super.setController(controller);
        this.DEBUGcontroller = controller;
    }
}
