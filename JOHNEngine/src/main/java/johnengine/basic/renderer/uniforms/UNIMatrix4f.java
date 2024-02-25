package johnengine.basic.renderer.uniforms;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryStack;

public class UNIMatrix4f extends AUniform<Matrix4f> {

    public UNIMatrix4f(String name, String identifier) {
        super(name, identifier);
    }
    
    public UNIMatrix4f(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    @Override
    public void set() {
        try( MemoryStack stack = MemoryStack.stackPush() )
        {
            GL46.glUniformMatrix4fv(this.location, false, this.value.get(stack.mallocFloat(16)));
        }
    }
    
    @Override
    protected Matrix4f getDefault() {
        return new Matrix4f();
    }
}
