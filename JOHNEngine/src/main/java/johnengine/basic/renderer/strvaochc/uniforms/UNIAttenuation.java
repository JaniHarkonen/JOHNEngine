package johnengine.basic.renderer.strvaochc.uniforms;

import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.strvaochc.structs.SAttenuation;
import johnengine.basic.renderer.uniforms.AUniform;
import johnengine.basic.renderer.uniforms.UNIFloat;
import johnengine.basic.renderer.uniforms.UniformUtils;

public class UNIAttenuation extends AUniform<SAttenuation> {
    
    private UNIFloat fConstant;
    private UNIFloat fLinear;
    private UNIFloat fExponent;

    public UNIAttenuation(String name, String identifier) {
        super(name, identifier);
        this.fConstant = new UNIFloat(
            UniformUtils.addFieldName(this.name, "constant"),
            UniformUtils.addFieldNameToId(this.identifier, "fConstant")
        );
        
        this.fLinear = new UNIFloat(
            UniformUtils.addFieldName(this.name, "linear"),
            UniformUtils.addFieldNameToId(this.identifier, "fLinear")
        );
        
        this.fExponent = new UNIFloat(
            UniformUtils.addFieldName(this.name, "exponent"),
            UniformUtils.addFieldNameToId(this.identifier, "fExponent")
        );
    }

    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        this.fConstant.declare(shaderProgram);
        this.fLinear.declare(shaderProgram);
        this.fExponent.declare(shaderProgram);
    }
    
    @Override
    public void set() {
        this.fConstant.set(this.value.fConstant);
        this.fLinear.set(this.value.fLinear);
        this.fExponent.set(this.value.fExponent);
    }

    @Override
    protected SAttenuation getDefault() {
        return new SAttenuation();
    }
    
    
    public UNIFloat getUniformConstant() {
        return this.fConstant;
    }
    
    public UNIFloat getUniformLinear() {
        return this.fLinear;
    }
    
    public UNIFloat getUniformExponent() {
        return this.fExponent;
    }
}
