package johnengine.basic.opengl.renderer.cachedvao.uniforms;

import johnengine.basic.opengl.renderer.cachedvao.structs.SAmbientLight;
import johnengine.basic.opengl.renderer.uniforms.AUniformObject;
import johnengine.basic.opengl.renderer.uniforms.UNIFloat;
import johnengine.basic.opengl.renderer.uniforms.UNIVector3f;

public class UNIAmbientLight extends AUniformObject<SAmbientLight> {

    private UNIVector3f c3Ambient;
    private UNIFloat fIntensity;
    
    public UNIAmbientLight(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIVector3f("ambient", "c3Ambient"), 
            new UNIFloat("intensity", "fIntensity")
        );
        
        this.c3Ambient = (UNIVector3f) this.uniformFields.get("ambient");
        this.fIntensity = (UNIFloat) this.uniformFields.get("intensity");
    }
    
    @Override
    public void setValue(SAmbientLight struct) {
        this.c3Ambient.setValue(struct.c3Ambient);
        this.fIntensity.setValue(struct.fIntensity);
    }
    
    
    public UNIVector3f getUniformAmbient() {
        return this.c3Ambient;
    }
    
    public UNIFloat getUniformIntensity() {
        return this.fIntensity;
    }
}
