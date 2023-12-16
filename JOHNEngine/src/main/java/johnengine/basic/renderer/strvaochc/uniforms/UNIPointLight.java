package johnengine.basic.renderer.strvaochc.uniforms;

import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;
import johnengine.basic.renderer.uniforms.AUniform;
import johnengine.basic.renderer.uniforms.UNIFloat;
import johnengine.basic.renderer.uniforms.UNIVector3f;
import johnengine.basic.renderer.uniforms.UniformUtils;

public class UNIPointLight extends AUniform<SPointLight> {
    
    private UNIVector3f c3Light;
    private UNIVector3f v3Position;
    private UNIFloat fIntensity;
    private UNIAttenuation attenuation;

    public UNIPointLight(String name, String identifier) {
        super(name, identifier);
        
        this.c3Light = new UNIVector3f(
            UniformUtils.addFieldName(this.name, "ligthColor"),
            UniformUtils.addFieldNameToId(this.identifier, "c3Light")
        );
        
        this.v3Position = new UNIVector3f(
            UniformUtils.addFieldName(this.name, "position"),
            UniformUtils.addFieldNameToId(this.identifier, "v3Position")
        );
        
        this.fIntensity = new UNIFloat(
            UniformUtils.addFieldName(this.name, "intensity"),
            UniformUtils.addFieldNameToId(this.identifier, "fIntensity")
        );
        
        this.attenuation = new UNIAttenuation(
            UniformUtils.addFieldName(this.name, "attenuation"),
            UniformUtils.addFieldNameToId(this.identifier, "attenuation")
        );
    }
    
    public UNIPointLight(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        this.c3Light.declare(shaderProgram);
        this.v3Position.declare(shaderProgram);
        this.fIntensity.declare(shaderProgram);
        this.attenuation.declare(shaderProgram);
    }
    
    @Override
    public void set() {
        this.c3Light.set(this.value.c3Light);
        this.v3Position.set(this.value.v3Position);
        this.fIntensity.set(this.value.fIntensity);
        this.attenuation.set(this.value.attenuation);
    }

    @Override
    protected SPointLight getDefault() {
        return new SPointLight();
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
