package johnengine.basic.game.components.geometry.rewrite;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CRotation implements IGeometryComponent<Quaternionf> {

    public static final Vector3f DEFAULT_ROTATION = new Vector3f(0.0f);
    
    private static final Vector3f X_AXIS = new Vector3f(1.0f, 0.0f, 0.0f);
    private static final Vector3f Y_AXIS = new Vector3f(0.0f, 1.0f, 0.0f);
    private static final Vector3f Z_AXIS = new Vector3f(0.0f, 0.0f, 1.0f);
    
    private Vector3f base;
    private Vector3f offset;
    private Quaternionf rotation;
    
    public CRotation(float x, float y, float z) {
        this.base = new Vector3f(x, y, z);
        this.offset = new Vector3f(0.0f);
        this.rotation = new Quaternionf();
    }
    
    public CRotation(Vector3f rotation) {
        this(rotation.x, rotation.y, rotation.z);
    }
    
    public CRotation() {
        this(DEFAULT_ROTATION);
    }
    
    
    @Override
    public void calculate() {
        this.rotation.from
    }
    
    public void rotateX(float angle) {
        this.xAngle += angle;
        this.rotation.fromAxisAngleDeg(X_AXIS, this.xAngle).mul((new Quaternionf()).fromAxisAngleDeg(Y_AXIS, this.yAngle));
    }
    
    public void rotateY(float angle) {
        this.yAngle += angle;
        this.rotation.fromAxisAngleDeg(X_AXIS, this.xAngle).mul((new Quaternionf()).fromAxisAngleDeg(Y_AXIS, this.yAngle));
    }
    
    
    @Override
    public Quaternionf get() {
        return this.rotation;
    }
    
    @Override
    public Quaternionf getCopy() {
        return new Quaternionf(this.rotation);
    }
    
    @Override
    public void set(Quaternionf rotation) {
        this.rotation = rotation;
    }
}
