package johnengine.basic.renderer.uniforms;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

public class UNIVector3f extends AUniform<Vector3f> {

    public UNIVector3f(String name, String identifier) {
        super(name, identifier);
    }
    
    public UNIVector3f(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    
    @Override
    public void set() {
        GL46.glUniform3f(this.location, this.value.x, this.value.y, this.value.z);
    }
    
    @Override
    protected Vector3f getDefault() {
        return new Vector3f(0.0f, 0.0f, 0.0f);
    }
}
