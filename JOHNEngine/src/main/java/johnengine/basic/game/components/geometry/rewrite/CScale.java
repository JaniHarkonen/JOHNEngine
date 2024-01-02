package johnengine.basic.game.components.geometry.rewrite;

import org.joml.Vector3f;

public class CScale implements IGeometryComponent<Vector3f> {
    
    public static final Vector3f DEFAULT_BASE = new Vector3f(1.0f, 1.0f, 1.0f);

    private Vector3f base;
    private Vector3f offset;
    private Vector3f scale;
    
    public CScale(float xScale, float yScale, float zScale) {
        this.base = new Vector3f(xScale, yScale, zScale);
        this.offset = new Vector3f(0.0f);
        this.scale = new Vector3f(0.0f);
    }
    
    public CScale(Vector3f scale) {
        this(scale.x, scale.y, scale.z);
    }
    
    public CScale() {
        this(DEFAULT_BASE);
    }
    
    
    @Override
    public void calculate() {
        this.scale.set(
            this.base.x + this.offset.x, 
            this.base.y + this.offset.y, 
            this.base.z + this.offset.z
        );
    }
    
    @Override
    public Vector3f get() {
        this.calculate();
        return this.scale;
    }

    @Override
    public Vector3f getCopy() {
        this.calculate();
        return new Vector3f(this.scale);
    }
    
    
    public void setBase(Vector3f base) {
        this.base = base;
    }
    
    public void setParent(CScale parent) {
        Vector3f otherScale = parent.scale;
        Vector3f scale = this.scale;
        this.offset.set(
            scale.x - otherScale.x, 
            scale.y - otherScale.y, 
            scale.z - otherScale.z
        );
        this.setBase(parent.scale);
    }
    
    public void unparent() {
        this.setBase(new Vector3f(this.base));
    }
    
    public void scale(float xScale, float yScale, float zScale) {
        this.base.add(xScale, yScale, zScale);
    }
    
    public void scale(Vector3f scale) {
        this.scale(scale.x, scale.y, scale.z);
    }
    
    public void set(float xScale, float yScale, float zScale) {
        this.base.set(xScale, yScale, zScale);
    }
    
    public void set(Vector3f scale) {
        this.set(scale.x, scale.y, scale.z);
    }
}
