    package johnengine.basic.game.components.geometry;

import org.joml.Vector3f;

public class CPosition2 implements IGeometryComponent.DirectAccess<Vector3f> {

    private final Vector3f position;
    private Vector3f offset;
    
    public CPosition2(Vector3f position) {
        this.position = position;
        this.offset = new Vector3f(0.0f);
    }
    
    public CPosition2() {
        this(new Vector3f());
    }
    
    
    public void move(Vector3f position) {
        this.position.add(position);
    }
    
    
    @Override
    public Vector3f get() {
        return this.position.add(v);
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
