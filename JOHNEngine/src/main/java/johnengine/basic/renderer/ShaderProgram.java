package johnengine.basic.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;
import johnengine.basic.renderer.asset.Shader;
import johnengine.basic.renderer.uniforms.IUniform;

public class ShaderProgram implements IGeneratable, IBindable {
    private List<Shader> shaders;
    private Map<String, IUniform<?>> uniforms;
    private int handle;

    public ShaderProgram() {
        this.shaders = new ArrayList<>();
        this.uniforms = new HashMap<>();
        this.handle = 0;
    }
    
    
    @Override
    public boolean generate() {
        if( this.handle > 0 )
        return false;
        
        this.handle = GL46.glCreateProgram();
        
        for( Shader shader : this.shaders )
        shader.generateAndAttach(this);
        
        GL46.glLinkProgram(this.handle);
        
        for( Shader shader : this.shaders )
        shader.detachFrom(this);
        
        return true;
    }
    
    @Override
    public boolean bind() {
        GL46.glUseProgram(this.handle);
        return true;
    }
    
    @Override
    public boolean unbind() {
        GL46.glUseProgram(0);
        return true;
    }
    
    @Override
    public boolean dispose() {
        this.unbind();
        GL46.glDeleteProgram(this.handle);
        this.uniforms = null;
        this.shaders = null;
        this.handle = 0;
        
        return true;
    }
    
    
    public ShaderProgram declareUniform(IUniform<?> uniform) {
        uniform.declare(this);
        this.uniforms.put(uniform.getName(), uniform);
        
        return this;
    }
    
    public IUniform<?> getUniform(String name) {
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
