package johnengine.basic.game.lights;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.core.renderer.IRenderStrategy;

public class JSpotLight extends AWorldObject {

    public static final Vector3f DEFAULT_CONE_DIRECTION = new Vector3f(0.5f, 0.5f, 0.0f);
    public static final float DEFAULT_CUT_OFF_ANGLE = 12.0f;
    
    private Vector3f coneDirection;
    private float cutOff;
    private float cutOffAngle;
    private JPointLight pointLight;
    
    public JSpotLight(JWorld world) {
        super(world);
        this.coneDirection = DEFAULT_CONE_DIRECTION;
        this.cutOffAngle = DEFAULT_CUT_OFF_ANGLE;
    }
    
    
    @Override
    public void render(IRenderStrategy renderStrategy) {
        this.pointLight.render(renderStrategy);
        renderStrategy.executeStrategoid(this);
    }

    @Override
    public void tick(float deltaTime) {
        this.pointLight.getPosition().set(this.position.get());
    }

    
    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }
    
    public void setCutOffAngle(float cutOffAngle) {
        this.cutOffAngle = cutOffAngle;
        this.cutOff = (float) Math.cos(Math.toRadians(this.cutOffAngle));
    }
    
    public void setPointLight(JPointLight pointLight) {
        this.pointLight = pointLight;
    }
    

    public Vector3f getConeDirection() {
        return this.coneDirection;
    }
    
    public float getCutOffAngle() {
        return this.cutOffAngle;
    }
    
    public float getCutOff() {
        return this.cutOff;
    }
    
    public JPointLight getPointLight() {
        return this.pointLight;
    }
}
