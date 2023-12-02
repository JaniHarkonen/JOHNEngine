package johnengine.basic.assets.opengl.shader.uniforms;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.opengl.shader.AUniform;

public class UNIInteger extends AUniform<Integer> {

    public UNIInteger(String name, String identifier) {
        super(name, identifier);
    }
    
    public UNIInteger(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    
    @Override
    public void set() {
        GL30.glUniform1i(this.location, this.value);
    }
    
    @Override
    protected Integer getDefault() {
        return 0;
    }
}
