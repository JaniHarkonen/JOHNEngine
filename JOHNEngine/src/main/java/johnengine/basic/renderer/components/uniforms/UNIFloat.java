package johnengine.basic.renderer.components.uniforms;

import org.lwjgl.opengl.GL30;

import johnengine.basic.renderer.components.AUniform;

public class UNIFloat extends AUniform<Float> {

    public UNIFloat(String name, String identifier) {
        super(name, identifier);
    }
    
    public UNIFloat(String nameAndIdentifier) {
        this(nameAndIdentifier, nameAndIdentifier);
    }

    
    @Override
    public void set() {
        GL30.glUniform1f(this.location, this.value);
    }
    
    @Override
    protected Float getDefault() {
        return 0.0f;
    }
}
