package johnengine.basic.opengl.renderer.uniforms;

import org.lwjgl.opengl.GL46;

public class UNIInteger extends AUniform<Integer> {

    public UNIInteger(String name, String identifier) {
        super(name, identifier);
    }
    
    public UNIInteger(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    
    @Override
    public void set() {
        GL46.glUniform1i(this.location, this.value);
    }
    
    @Override
    protected Integer getDefault() {
        return 0;
    }
}
