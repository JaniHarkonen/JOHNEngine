package johnengine.core.renderer.unimngr;

import org.lwjgl.opengl.GL30;

public class UNIMatrix1I extends AUniform<Integer> {

    public UNIMatrix1I(String name, Integer value) {
        super(name, value);
    }
    
    public UNIMatrix1I(String name) {
        this(name, 0);
    }

    @Override
    public void set() {
        GL30.glUniform1i(this.getLocation(), this.getValue());
    }
}
