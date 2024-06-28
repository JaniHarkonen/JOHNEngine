package johnengine.basic.game.lights;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.IWorld;

public class JAmbientLight extends AWorldObject {

    public static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final float DEFAULT_INTENSITY = 0.5f;
    
    private Vector3f color;
    private float intensity;
    
    public JAmbientLight(IWorld world) {
        super(world);
        this.color = DEFAULT_COLOR;
        this.intensity = DEFAULT_INTENSITY;
    }
    

    @Override
    public void tick(float deltaTime) {
        //this.intensity = (float) Math.random();
        //this.color = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());
    }
    
    
    public Vector3f getColor() {
        return this.color;
    }
    
    public float getIntensity() {
        return this.intensity;
    }
    
    
    public void setColor(Vector3f color) {
        this.color = color;
    }
    
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
