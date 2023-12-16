package johnengine.basic.renderer.strvaochc.uniforms;

import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.renderer.uniforms.AUniform;
import johnengine.basic.renderer.uniforms.UNIFloat;
import johnengine.basic.renderer.uniforms.UNIVector3f;
import johnengine.basic.renderer.uniforms.UniformUtils;

public class UNIAmbientLight extends AUniform<SAmbientLight> {
    
    private UNIVector3f c3Ambient;
    private UNIFloat fIntensity;

    public UNIAmbientLight(String name, String identifier) {
        super(name, identifier);
        this.c3Ambient = new UNIVector3f(
            UniformUtils.addFieldName(this.name, "ambientLight"),
            UniformUtils.addFieldNameToId(this.identifier, "c3Ambient")
        );
        
        this.fIntensity = new UNIFloat(
            UniformUtils.addFieldName(this.name, "intensity"),
            UniformUtils.addFieldNameToId(this.identifier, "fIntensity")
        );
    }

    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        this.fIntensity.declare(shaderProgram);
        this.c3Ambient.declare(shaderProgram);
    }
    
    @Override
    public void set() {
        this.c3Ambient.set(this.value.c3Ambient);
        this.fIntensity.set(this.value.fIntensity);
    }

    @Override
    protected SAmbientLight getDefault() {
        return new SAmbientLight();
    }
    
    
    public UNIVector3f getUniformAmbient() {
        return this.c3Ambient;
    }
    
    public UNIFloat getUniformIntensity() {
        return this.fIntensity;
    }
}
