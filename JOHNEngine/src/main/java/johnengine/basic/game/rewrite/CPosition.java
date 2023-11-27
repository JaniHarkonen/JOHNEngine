package johnengine.basic.game.rewrite;

import org.joml.Vector3f;

public class CPosition {

    private Vector3f position;
    
    public CPosition(Vector3f position) {
        this.position = position;
    }
    
    public CPosition() {
        this(new Vector3f());
    }
    
    
    public Vector3f get() {
        return this.position;
    }
    
    public Vector3f getCopy() {
        return new Vector3f(this.position);
    }
    
    public void set(Vector3f position) {
        this.position = position;
    }
}
