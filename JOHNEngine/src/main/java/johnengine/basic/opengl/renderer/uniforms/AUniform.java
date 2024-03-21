package johnengine.basic.opengl.renderer.uniforms;

import org.lwjgl.opengl.GL46;

import johnengine.basic.opengl.renderer.ShaderProgram;

public abstract class AUniform<T> implements IUniform<T> {
    protected String name;
    protected String identifier;
    protected int location;
    protected T value;
    protected IUniform<?> parent;
    
    public AUniform(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
        this.location = -1;
        this.value = this.getDefault();
        this.parent = null;
    }
    
    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        this.location = GL46.glGetUniformLocation(shaderProgram.getHandle(), this.identifier);
    }
    
    public abstract void set();
    
    protected abstract T getDefault();
    
    
    @Override
    public void setValue(T value) {
        this.value = value;
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
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
