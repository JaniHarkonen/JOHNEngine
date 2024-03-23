package johnengine.basic.opengl.renderer.cachedvao.uniforms;

import johnengine.basic.opengl.renderer.cachedvao.structs.SMaterial;
import johnengine.basic.opengl.renderer.uniforms.AUniformObject;
import johnengine.basic.opengl.renderer.uniforms.UNIFloat;
import johnengine.basic.opengl.renderer.uniforms.UNIVector4f;

public class UNIMaterial extends AUniformObject<SMaterial> {

    private UNIVector4f c4Diffuse;
    private UNIVector4f c4Specular;
    private UNIFloat fReflectance;    
    
    public UNIMaterial(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIVector4f("diffuse", "c4Diffuse"),
            new UNIVector4f("specular", "c4Specular"),
            new UNIFloat("reflectance", "fReflectance")
        );
        
        this.c4Diffuse = (UNIVector4f) this.uniformFields.get("diffuse");
        this.c4Specular = (UNIVector4f) this.uniformFields.get("specular");
        this.fReflectance = (UNIFloat) this.uniformFields.get("reflectance");
    }
    
    public UNIMaterial() {
        this("", "");
    }
    
    
    @Override
    public void setValue(SMaterial struct) {
        this.c4Diffuse.setValue(struct.c4Diffuse);
        this.c4Specular.setValue(struct.c4Specular);
        this.fReflectance.setValue(struct.fReflectance);
    }
    
    
    public UNIVector4f getUniformDiffuse() {
        return this.c4Diffuse;
    }
    
    public UNIVector4f getUniformSpecular() {
        return this.c4Specular;
    }
    
    public UNIFloat getUniformReflectance() {
        return this.fReflectance;
    }
}
