package johnengine.basic.renderer.strvaochc.uniforms;

import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.strvaochc.structs.SMaterial;
import johnengine.basic.renderer.uniforms.AUniform;
import johnengine.basic.renderer.uniforms.UNIFloat;
import johnengine.basic.renderer.uniforms.UNIVector4f;
import johnengine.basic.renderer.uniforms.UniformUtils;

public class UNIMaterial extends AUniform<SMaterial> {
    
    private UNIVector4f c4Diffuse;
    private UNIVector4f c4Specular;
    private UNIFloat fReflectance;

    public UNIMaterial(String name, String identifier) {
        super(name, identifier);
        this.c4Diffuse = new UNIVector4f(
            UniformUtils.addFieldName(this.name, "diffuseLight"),
            UniformUtils.addFieldNameToId(this.identifier, "c4Diffuse")
        );
        
        this.c4Specular = new UNIVector4f(
            UniformUtils.addFieldName(this.name, "specularLight"),
            UniformUtils.addFieldNameToId(this.identifier, "c4Specular")
        );
        
        this.fReflectance = new UNIFloat(
            UniformUtils.addFieldName(this.name, "reflectance"),
            UniformUtils.addFieldNameToId(this.identifier, "fReflectance")
        );
    }

    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        this.c4Diffuse.declare(shaderProgram);
        this.c4Specular.declare(shaderProgram);
        this.fReflectance.declare(shaderProgram);
    }
    
    @Override
    public void set() {
        this.c4Diffuse.set(this.value.c4Diffuse);
        this.c4Specular.set(this.value.c4Specular);
        this.fReflectance.set(this.value.fReflectance);
    }

    @Override
    protected SMaterial getDefault() {
        return new SMaterial();
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
