package johnengine.basic.renderer.uniforms;

import johnengine.basic.renderer.ShaderProgram;

public interface IUniform<T> {

    public void declare(ShaderProgram shaderProgram);
    
    public void setValue(T value);
    
    public void set();
    
    default public void set(T value) {
        this.setValue(value);
        this.set();
    }
    
    public String getName();
    
    public String getIdentifier();
    
    public void setName(String name);
    
    public void setIdentifier(String identifier);
}
