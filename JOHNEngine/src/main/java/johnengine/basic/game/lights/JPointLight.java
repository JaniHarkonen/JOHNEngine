package johnengine.basic.game.lights;

import org.joml.Vector3f;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CAttenuation;
import johnengine.basic.game.components.CModel;
import johnengine.core.renderer.IRenderStrategy;

public class JPointLight extends AWorldObject {

    public static final float DEFAULT_INTENSITY = 10.0f;
    public static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 0.0f, 0.0f);
    
    private float intensity;
    private Vector3f color;
    private CAttenuation attenuation;
    
    //REMOVEEE
    public CModel DEBUGmodel;
    
    public JPointLight(JWorld world) {
        super(world);
        this.intensity = DEFAULT_INTENSITY;
        this.color = DEFAULT_COLOR;
        this.attenuation = new CAttenuation();
        this.getPosition().set(new Vector3f(0.0f, 2.0f, 0.0f));
    }

    @Override
    public void render(IRenderStrategy renderStrategy) {
        renderStrategy.executeStrategoid(this);
        
        if( this.DEBUGmodel != null )
        {
            this.DEBUGmodel.getScale().set(new Vector3f(.1f));
            this.DEBUGmodel.setPosition(this.position);
            this.DEBUGmodel.render(renderStrategy);
        }
    }

    @Override
    public void tick(float deltaTime) {
        //this.position.set(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
        //this.position.move(new Vector3f(0.0f, 0.0f, 0.01f));
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
