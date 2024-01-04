package johnengine.basic.game.lights;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;

public class JDirectionalLight extends AWorldObject {

    public static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final Vector3f DEFAULT_DIRECTION = new Vector3f(0.0f, -1.0f, 0.0f);
    public static final float DEFAULT_INTENSITY = 1.0f;
    
    private Vector3f color;
    private Vector3f direction;
    private float intensity;
    
    public JDirectionalLight(JWorld world) {
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
        
    }
}
