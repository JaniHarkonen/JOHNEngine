package johnengine.basic.game.components.geometry.rewrite;

import org.joml.Vector3f;


public class CPosition implements IGeometryComponent<Vector3f> {
    public static final Vector3f DEFAULT_ORIGIN = new Vector3f(0.0f);
    
    private Vector3f origin;
    private Vector3f offset;
    private Vector3f position;
    
    public CPosition(float x, float y, float z) {
        this.origin = new Vector3f(x, y, z);
        this.offset = new Vector3f(0.0f);
        this.position = new Vector3f(0.0f);
    }
    
    public CPosition(Vector3f origin) {
        this(origin.x, origin.y, origin.z);
    }
    
    public CPosition() {
        this(DEFAULT_ORIGIN);
    }
    
    
    @Override
    public void calculate() {
        this.position.set(
            this.origin.x + this.offset.x, 
            this.origin.y + this.offset.y, 
            this.origin.z + this.offset.z
        );
    }
    
    @Override
    public Vector3f get() {
        this.calculate();
        return this.position;
    }

    @Override
    public Vector3f getCopy() {
        this.calculate();
        return new Vector3f(this.position);
    }
    
    public void setOrigin(Vector3f origin) {
        this.origin = origin;
    }
    
    public void setParent(CPosition parent) {
        Vector3f otherPosition = parent.position;
        Vector3f position = this.position;
        this.offset.set(
            position.x - otherPosition.x, 
            position.y - otherPosition.y, 
            position.z - otherPosition.z
        );
        this.setOrigin(parent.position);
    }
    
    public void unparent() {
        this.setOrigin(new Vector3f(this.origin));
    }
    
    public void shift(float x, float y, float z) {
        this.origin.add(x, y, z);
        this.calculate();
    }
    
    public void shift(Vector3f shiftVector) {
        this.shift(shiftVector.x, shiftVector.y, shiftVector.z);
    }
    
    public void move(float x, float y, float z) {
        this.origin.set(x, y, z);
        this.calculate();
    }
    
    public void move(Vector3f position) {
        this.move(position.x, position.y, position.z);
    }
    
    public Vector3f getget() {
        return this.position;
    }
}
