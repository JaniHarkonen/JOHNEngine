package johnengine.basic.game;

import org.joml.Vector2f;

public class CRotation {

    private Vector2f rotation;
    
    public CRotation(Vector2f rotation) {
        this.rotation = rotation;
    }
    
    public CRotation() {
        this(new Vector2f());
    }
    
    
    public void rotate(float rotationX, float rotationY) {
        this.rotation.set(this.rotation.x + rotationX, this.rotation.y + rotationY);
    }
    
    
    public Vector2f get() {
        return this.rotation;
    }
    
    public Vector2f getCopy() {
        return new Vector2f(this.rotation);
    }
    
    public void set(Vector2f rotation) {
        this.rotation = rotation;
    }
}
