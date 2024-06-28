package johnengine.basic.opengl.renderer.cachedvao.uniforms;

import johnengine.basic.opengl.renderer.cachedvao.structs.SDirectionalLight;
import johnengine.basic.opengl.renderer.uniforms.AUniformObject;
import johnengine.basic.opengl.renderer.uniforms.UNIFloat;
import johnengine.basic.opengl.renderer.uniforms.UNIVector3f;

public class UNIDirectionalLight extends AUniformObject<SDirectionalLight> {

    private UNIVector3f c3Light;
    private UNIVector3f v3Direction;
    private UNIFloat fIntensity;
    
    public UNIDirectionalLight(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIVector3f("light", "c3Light"),
            new UNIVector3f("direction", "v3Direction"),
            new UNIFloat("intensity", "fIntensity")
        );
        
        this.c3Light = (UNIVector3f) this.uniformFields.get("light");
        this.v3Direction = (UNIVector3f) this.uniformFields.get("direction");
        this.fIntensity = (UNIFloat) this.uniformFields.get("intensity");
    }
    
    public UNIDirectionalLight() {
        this("", "");
    }

    
    @Override
    public void setValue(SDirectionalLight struct) {
        this.c3Light.setValue(struct.c3Light);
        this.v3Direction.setValue(struct.v3Direction);
        this.fIntensity.setValue(struct.fIntensity);
    }
    
    
    public UNIVector3f getUniformLight() {
        return this.c3Light;
    }
    
    public UNIVector3f getUniformDirection() {
        return this.v3Direction;
    }
    
    public UNIFloat getUniformIntensity() {
        return this.fIntensity;
    }
}
