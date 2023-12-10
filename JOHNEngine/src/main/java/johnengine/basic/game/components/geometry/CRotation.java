package johnengine.basic.game.components.geometry;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.IGeometryComponent.DirectAccess;

public class CRotation implements IGeometryComponent.DirectAccess<Quaternionf> {

    private static final Vector3f X_AXIS = new Vector3f(1.0f, 0.0f, 0.0f);
    private static final Vector3f Y_AXIS = new Vector3f(0.0f, 1.0f, 0.0f);
    private static final Vector3f Z_AXIS = new Vector3f(0.0f, 0.0f, 1.0f);
    
    private Quaternionf rotation;
    private float xAngle;
    private float yAngle;
    private float zAngle;
    
    public CRotation() {
        this.rotation = (new Quaternionf()).fromAxisAngleDeg(Y_AXIS, 0.0f);
        this.xAngle = 0.0f;
        this.yAngle = 0.0f;
        this.zAngle = 0.0f;
    }
    
    
    public void rotateX(float angle) {
        this.xAngle += angle;
        this.rotation.fromAxisAngleDeg(X_AXIS, this.xAngle).mul((new Quaternionf()).fromAxisAngleDeg(Y_AXIS, this.yAngle));
        //this.xAngle += angle;
        //this.rotation.fromAxisAngleDeg(Y_AXIS, this.yAngle);
        //this.rotation.rotateAxis(this.xAngle, X_AXIS);
        //this.rotation.rotateX(angle);
        //this.rotation.rotateLocalX(angle);
        //this.rotation.rotateAxis(this.xAngle, X_AXIS);
    }
    
    public void rotateY(float angle) {
        this.yAngle += angle;
        this.rotation.fromAxisAngleDeg(X_AXIS, this.xAngle).mul((new Quaternionf()).fromAxisAngleDeg(Y_AXIS, this.yAngle));
        //this.xAngle += angle;
        //this.rotation.fromAxisAngleDeg(Y_AXIS, this.yAngle);
        //this.rotation.fromAxisAngleDeg(X_AXIS, this.xAngle);
        //this.rotation.rotateAxis(this.xAngle, X_AXIS);
        //this.rotation.rotateAxis(this.yAngle, Y_AXIS);
    }
    
    public void rotateZ(float angle) {
        //this.zAngle += angle;
        //this.rotation.rotateAxis(this.zAngle, Z_AXIS);
    }
    
    
    @Override
    public Quaternionf get() {
        return this.rotation;
    }
    
    @Override
    public Quaternionf getCopy() {
        return new Quaternionf(this.rotation);
    }
    
    public float getXAngle() {
        return this.xAngle;
    }
    
    public float getYAngle() {
        return this.yAngle;
    }
    
    public float getZAngle() {
        return this.zAngle;
    }
    
    @Override
    public void set(Quaternionf rotation) {
        this.rotation = rotation;
    }
}
