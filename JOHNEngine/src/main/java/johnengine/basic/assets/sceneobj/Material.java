package johnengine.basic.assets.sceneobj;

import org.joml.Vector4f;

import johnengine.basic.opengl.renderer.asset.Texture;

public class Material {

    public static final Vector4f DEFAULT_DIFFUSE_COLOR = new Vector4f(0.8f, 0.8f, 0.8f, 1.0f);
    public static final Vector4f DEFAULT_SPECULAR_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
    public static final float DEFAULT_REFLECTANCE = 0.4f;
    
    private Texture texture;
    private Texture normal;
    private Vector4f diffuseColor;
    private Vector4f specularColor;
    private float reflectance;
    
    public Material(Texture texture, Vector4f diffuseColor, Vector4f specularColor, float reflectance) {
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.texture = texture;
        this.reflectance = reflectance;
        this.normal = null;
    }
    
    public Material() {
        this(
            null, 
            DEFAULT_DIFFUSE_COLOR,
            DEFAULT_SPECULAR_COLOR,
            DEFAULT_REFLECTANCE
        );
    }
    
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    public void setNormalMap(Texture normal) {
        this.normal = normal;
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
