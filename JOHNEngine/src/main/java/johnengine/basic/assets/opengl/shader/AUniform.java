package johnengine.basic.assets.opengl.shader;

import org.lwjgl.opengl.GL30;

public abstract class AUniform<T> {
    protected final String name;
    protected final String identifier;
    protected int location;
    protected T value;
    
    protected AUniform(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
        this.location = -1;
        this.value = this.getDefault();
    }
    
    
    public void declare(ShaderProgram shaderProgram) {
        this.location = GL30.glGetUniformLocation(shaderProgram.getHandle(), this.identifier);
    }
    
    public abstract void set();
    
    public void set(T value) {
        this.setValue(value);
        this.set();
    }
    
    protected abstract T getDefault();
    
    
    public void setValue(T value) {
        this.value = value;
    }
    
    
    public String getName() {
        return this.name;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public int getLocation() {
        return this.location;
    }
    
    public T getValue() {
        return this.value;
    }
}
