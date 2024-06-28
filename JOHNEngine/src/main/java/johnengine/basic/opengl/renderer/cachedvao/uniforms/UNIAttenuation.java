package johnengine.basic.opengl.renderer.cachedvao.uniforms;

import johnengine.basic.opengl.renderer.cachedvao.structs.SAttenuation;
import johnengine.basic.opengl.renderer.uniforms.AUniformObject;
import johnengine.basic.opengl.renderer.uniforms.UNIFloat;

public class UNIAttenuation extends AUniformObject<SAttenuation> {

    private UNIFloat fConstant;
    private UNIFloat fLinear;
    private UNIFloat fExponent;
    
    public UNIAttenuation(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIFloat("constant", "fConstant"),
            new UNIFloat("linear", "fLinear"),
            new UNIFloat("exponent", "fExponent")
        );
        
        this.fConstant = (UNIFloat) this.uniformFields.get("constant");
        this.fLinear = (UNIFloat) this.uniformFields.get("linear");
        this.fExponent = (UNIFloat) this.uniformFields.get("exponent");
    }
    
    public UNIAttenuation() {
        this("", "");
    }
    
    
    @Override
    public void setValue(SAttenuation struct) {
        this.fConstant.setValue(struct.fConstant);
        this.fLinear.setValue(struct.fLinear);
        this.fExponent.setValue(struct.fExponent);
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
