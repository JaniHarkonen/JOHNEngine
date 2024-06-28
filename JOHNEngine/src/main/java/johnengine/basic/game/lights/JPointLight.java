package johnengine.basic.game.lights;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.IWorld;
import johnengine.basic.game.components.CAttenuation;

public class JPointLight extends AWorldObject {

    public static final float DEFAULT_INTENSITY = 50.0f;
    public static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);
    
    private float intensity;
    private Vector3f color;
    private CAttenuation attenuation;
    
    public JPointLight(IWorld world) {
        super(world);
        this.intensity = DEFAULT_INTENSITY;
        this.color = DEFAULT_COLOR;
        this.attenuation = new CAttenuation();
    }

    @Override
    public void tick(float deltaTime) {
        //this.intensity += 0.01f;
        //Vector3f dir = new Vector3f();
        //this.transform.getPosition().shift(this.transform.getRotation().getForwardVector(new Vector3f()).mul(0.01f));
        //this.transform.getPosition().setPosition(-57f/25f, 86f/25f, -85f/25f);
        this.transform.getPosition().setPosition(15, 3, 15);
        //this.transform.getPosition().setPosition(this.transform.getPosition().get().x, this.transform.getPosition().get().y, 3);
        //this.transform.getPosition().shift(0.0f, -0.01f, 0f);
    }

    
    public float getIntensity() {
        return this.intensity;
    }
    
    public Vector3f getColor() {
        return this.color;
    }
    
    public CAttenuation getAttenuation() {
        return this.attenuation;
    }
    
    
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
    public void setColor(Vector3f color) {
        this.color = color;
    }
    
    public void setAttenuation(CAttenuation attenuation) {
        this.attenuation = attenuation;
    }
}
