package johnengine.core.renderer.shdprog;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.lwjgl.opengl.GL30;

import johnengine.core.renderer.unimngr.AUniform;
import johnengine.core.renderer.unimngr.UniformManager;

public class ShaderProgram {
    
    public static final String FRAGMENT_SHADER = "fragment-shader";
    public static final String VERTEX_SHADER = "vertex-shader";
    
    private Map<String, Shader> shaders;
    private String[] uniformNames;
    private AUniform<?>[] uniformCache;
    private int programHandle;

    public ShaderProgram(String... uniformNames) {
        this.shaders = new HashMap<String, Shader>();
        this.uniformNames = uniformNames;
        this.uniformCache = new AUniform<?>[this.uniformNames.length];
    }
    
    
    public void setup(UniformManager uniformManager) {
        Set<Entry<String, Shader>> entrySet = this.shaders.entrySet();
        this.programHandle = GL30.glCreateProgram();
        
            // Cache the uniforms that will be used by this shader program
        for( int i = 0; i < this.uniformNames.length; i++ )
        this.uniformCache[i] = uniformManager.getUniform(this.uniformNames[i]);
        
        for( Entry<String, Shader> en : entrySet )
        this.shaders.get(en.getKey()).attach(this.programHandle);
        
        GL30.glLinkProgram(this.programHandle);
        
        for( Entry<String, Shader> en : entrySet )
        this.shaders.get(en.getKey()).deload();
    }
    
    public void bind() {
        GL30.glUseProgram(this.programHandle);
        
        for( AUniform<?> uniform : this.uniformCache )
        uniform.set();
    }
    
    public void unbind() {
        GL30.glUseProgram(0);
    }
    
    public void dispose() {
        this.unbind();
        GL30.glDeleteProgram(this.programHandle);
        this.programHandle = 0;
        this.uniformNames = null;
        this.uniformCache = null;
        this.shaders = null;
    }
    
    
    public void setShader(String shaderAlias, Shader shader) {
        this.shaders.put(shaderAlias, shader);
    }
    
    public void setFragmentShader(Shader shader) {
        this.setShader(FRAGMENT_SHADER, shader);
    }
    
    public void setVertexShader(Shader shader) {
        this.setShader(VERTEX_SHADER, shader);
    }
    
    public Shader getShader(String shaderAlias) {
        return this.shaders.get(shaderAlias);
    }
}
