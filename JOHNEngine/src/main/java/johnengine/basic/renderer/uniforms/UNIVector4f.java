package johnengine.basic.renderer.uniforms;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL30;

public class UNIVector4f extends AUniform<Vector4f> {

    public UNIVector4f(String name, String identifier) {
        super(name, identifier);
    }
    
    public UNIVector4f(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    
    @Override
    public void set() {
        GL30.glUniform4f(this.location, this.value.x, this.value.y, this.value.z, this.value.w);
    }
    
    @Override
    protected Vector4f getDefault() {
        return new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
