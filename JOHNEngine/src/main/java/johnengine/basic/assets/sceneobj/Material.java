package johnengine.basic.assets.sceneobj;

import org.joml.Vector4f;

import johnengine.Defaults;
import johnengine.basic.assets.texture.Texture;

public class Material {
    private Texture texture;
    private Texture normal;
    private Texture roughness;
    private Vector4f diffuseColor;
    private Vector4f specularColor;
    private float reflectance;
    
    public Material(
        Texture texture, 
        Vector4f diffuseColor, 
        Vector4f specularColor, 
        float reflectance
    ) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.texture = texture;
        this.reflectance = reflectance;
        this.normal = null;
        this.roughness = null;
    }
    
    public Material() {
        this(
            null, 
            Defaults.DEFAULT_DIFFUSE_COLOR,
            Defaults.DEFAULT_SPECULAR_COLOR,
            Defaults.DEFAULT_REFLECTANCE
        );
    }
    
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    public void setNormalMap(Texture normal) {
        this.normal = normal;
    }
    
    public void setRoughnessMap(Texture roughness) {
        this.roughness = roughness;
    }
    
    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }
    
    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }
    
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }
    
    
    public Texture getTexture() {
        return this.texture;
    }
    
    public Texture getNormalMap() {
        return this.normal;
    }
    
    public Texture getRoughnessMap() {
        return this.roughness;
    }
    
    public Vector4f getDiffuseColor() {
        return this.diffuseColor;
    }
    
    public Vector4f getSpecularColor() {
        return this.specularColor;
    }
    
    public float getReflectance() {
        return this.reflectance;
    }
}
