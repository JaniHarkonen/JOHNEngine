    package johnengine.basic.game;

import org.joml.Vector3f;

public class CPosition implements IGeometryComponent.DirectAccess<Vector3f> {

    private Vector3f position;
    
    public CPosition(Vector3f position) {
        this.position = position;
    }
    
    public CPosition() {
        this(new Vector3f());
    }
    
    
    public void move(Vector3f position) {
        this.position.add(position);
    }
    
    
    @Override
    public Vector3f get() {
        return this.position;
    }
    
    @Override
    public Vector3f getCopy() {
        return new Vector3f(this.position);
    }
    
    @Override
    public void set(Vector3f position) {
        this.position = position;
    }
}
