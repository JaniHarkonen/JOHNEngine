package johnengine.core.renderer.unimngr;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public class UNIMatrix4f extends AUniform<Matrix4f> {

    public UNIMatrix4f(String name, Matrix4f value) {
        super(name, value);
    }
    
    public UNIMatrix4f(String name) {
        this(name, null);
    }

    @Override
    public void set() {
        try( MemoryStack stack = MemoryStack.stackPush() )
        {
            GL30.glUniformMatrix4fv(this.getLocation(), false, this.getValue().get(stack.mallocFloat(16)));
        }
    }
}
