package johnengine.core.renderer.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL30;

public class ShaderProgram {
    private List<Shader> shaders;
    private Map<String, AUniform<?>> uniforms;
    private int handle;

    public ShaderProgram() {
        this.shaders = new ArrayList<Shader>();
        this.uniforms = new HashMap<String, AUniform<?>>();
        this.handle = 0;
    }
    
    
    public void generate() {
        if( this.handle > 0 )
        return;
        
        this.handle = GL30.glCreateProgram();
        
        for( Shader shader : this.shaders )
        shader.generateAndAttach(this);
        
        GL30.glLinkProgram(this.handle);
        
        for( Shader shader : this.shaders )
        shader.detachFrom(this);
    }
    
    public void bind() {
        GL30.glUseProgram(this.handle);
    }
    
    public void unbind() {
        GL30.glUseProgram(0);
    }
    
    public void dispose() {
        this.unbind();
        GL30.glDeleteProgram(this.handle);
        this.uniforms = null;
        this.shaders = null;
        this.handle = 0;
    }
    
    
    public ShaderProgram declareUniform(AUniform<?> uniform) {
        uniform.declare(this);
        this.uniforms.put(uniform.getName(), uniform);
        
        return this;
    }
    
    public AUniform<?> getUniform(String name) {
        return this.uniforms.get(name);
    }
    
    public ShaderProgram addShader(Shader shader) {
        this.shaders.add(shader);
        return this;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
