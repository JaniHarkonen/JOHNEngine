package johnengine.basic.opengl.renderer.strvaochc.uniforms;

import johnengine.basic.opengl.renderer.strvaochc.structs.SPointLight;
import johnengine.basic.opengl.renderer.uniforms.AUniformObject;
import johnengine.basic.opengl.renderer.uniforms.UNIFloat;
import johnengine.basic.opengl.renderer.uniforms.UNIVector3f;

public class UNIPointLight extends AUniformObject<SPointLight> {

    private UNIVector3f c3Light;
    private UNIVector3f v3Position;
    private UNIFloat fIntensity;
    private UNIAttenuation attenuation;
    
    public UNIPointLight(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIVector3f("light", "c3Light"),
            new UNIVector3f("position", "v3Position"),
            new UNIFloat("intensity", "fIntensity"),
            new UNIAttenuation("attenuation", "attenuation")
        );
        
        this.c3Light = (UNIVector3f) this.uniformFields.get("light");
        this.v3Position = (UNIVector3f) this.uniformFields.get("position");
        this.fIntensity = (UNIFloat) this.uniformFields.get("intensity");
        this.attenuation = (UNIAttenuation) this.uniformFields.get("attenuation");
    }
    
    public UNIPointLight() {
        this("", "");
    }

    
    @Override
    public void setValue(SPointLight value) {
        SPointLight struct = (SPointLight) value;
        this.c3Light.setValue(struct.c3Light);
        this.v3Position.setValue(struct.v3Position);
        this.fIntensity.setValue(struct.fIntensity);
        this.attenuation.setValue(struct.attenuation);
    }
    
    
    public UNIVector3f getUniformLight() {
        return this.c3Light;
    }
    
    public UNIVector3f getUniformPosition() {
        return this.v3Position;
    }
    
    public UNIFloat getUniformIntensity() {
        return this.fIntensity;
    }
    
    public UNIAttenuation getUniformAttenuation() {
        return this.attenuation;
    }
}
