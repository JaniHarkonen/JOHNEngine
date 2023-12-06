package johnengine.basic.game;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CRotation implements IGeometryComponent.Calculatable<Quaternionf> {

    private Quaternionf rotation;
    private float angle;
    private Vector3f axis;
    
    public CRotation() {
        this.rotation = new Quaternionf();
        this.angle = 0;
        this.axis = new Vector3f(0.0f, 1.0f, 0.0f);
        this.calculate();
    }
    
    
    @Override
    public void calculate() {
        this.rotation.fromAxisAngleRad(this.axis, this.angle);
    }
    
    public void rotate(float angle) {
        this.angle += angle;
        this.calculate();
    }
    
    @Override
    public Quaternionf get() {
        return this.rotation;
    }
    
    @Override
    public Quaternionf getCopy() {
        return new Quaternionf(this.rotation);
    }
    
    public float getAngle() {
        return this.angle;
    }
    
    public Vector3f getAxis() {
        return this.axis;
    }
    
    public void set(float angle) {
        this.angle = angle;
        this.calculate();
    }
    
    public void set(Vector3f axis, float angle) {
        this.axis = axis;
        this.angle = angle;
        this.calculate();
    }
}
