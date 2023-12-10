package johnengine.basic.game.components.geometry;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.IGeometryComponent.DirectAccess;

public class CScale implements IGeometryComponent.DirectAccess<Vector3f> {

    private Vector3f scale;
    
    public CScale(Vector3f scale) {
        this.scale = scale;
    }
    
    public CScale() {
        this(new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    
    public void resize(float scale) {
        this.scale.add(scale, scale, scale);
    }
    
    public void resizeX(float scale) {
        this.scale.add(scale, 0, 0);
    }
    
    public void resizeY(float scale) {
        this.scale.add(0, scale, 0);
    }
    
    public void resizeZ(float scale) {
        this.scale.add(0, 0, scale);
    }
    
    
    @Override
    public Vector3f get() {
        return this.scale;
    }
    
    @Override
    public Vector3f getCopy() {
        return new Vector3f(this.scale);
    }
    
    public float getXScale() {
        return this.scale.x;
    }
    
    public float getYScale() {
        return this.scale.y;
    }
    
    public float getZScale() {
        return this.scale.z;
    }
    
    
    @Override
    public void set(Vector3f value) {
        this.scale = value;
    }
}
