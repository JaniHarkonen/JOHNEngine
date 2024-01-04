package johnengine.basic.renderer.strvaochc.uniforms;

import johnengine.basic.renderer.strvaochc.structs.SSpotLight;
import johnengine.basic.renderer.uniforms.AUniformObject;
import johnengine.basic.renderer.uniforms.UNIFloat;
import johnengine.basic.renderer.uniforms.UNIVector3f;

public class UNISpotLight extends AUniformObject<SSpotLight> {

    private UNIVector3f v3Direction;
    private UNIFloat fCutOff;
    private UNIPointLight pointLight;
    
    public UNISpotLight(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIVector3f("direction", "v3Direction"),
            new UNIFloat("cutOff", "fCutOff"),
            new UNIPointLight("pointLight", "pointLight")
        );
        
        this.v3Direction = (UNIVector3f) this.uniformFields.get("direction");
        this.fCutOff = (UNIFloat) this.uniformFields.get("cutOff");
        this.pointLight = (UNIPointLight) this.uniformFields.get("pointLight");
    }
    
    public UNISpotLight() {
        this("", "");
    }

    
    @Override
    public void setValue(SSpotLight struct) {
        this.v3Direction.setValue(struct.v3Direction);
        this.fCutOff.setValue(struct.fCutOff);
        this.pointLight.setValue(struct.pointLight);
    }
    
    
    public UNIVector3f getUniformDirection() {
        return this.v3Direction;
    }
    
    public UNIFloat getUniformCutOff() {
        return this.fCutOff;
    }
    
    public UNIPointLight getUniformPointLight() {
        return this.pointLight;
    }
}
