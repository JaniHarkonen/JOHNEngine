package johnengine.core.renderer.shdprog;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL30;

public class ShaderProgram {
    
    public static final String FRAGMENT_SHADER = "fragment-shader";
    public static final String VERTEX_SHADER = "vertex-shader";
    
    private final Map<String, AShader> shaders;
    private int programHandle;

    public ShaderProgram() {
        this.shaders = new HashMap<String, AShader>();
    }
    
    
    public void setup() {
        this.programHandle = GL30.glCreateProgram();
        
        for( Map.Entry<String, AShader> en : this.shaders.entrySet() )
        this.shaders.get(en.getKey()).attach(this.programHandle);
        
        GL30.glLinkProgram(this.programHandle);
        
        for( Map.Entry<String, AShader> en : this.shaders.entrySet() )
        this.shaders.get(en.getKey()).deload();
    }
    
    public void bind() {
        GL30.glUseProgram(this.programHandle);
    }
    
    public void unbind() {
        GL30.glUseProgram(0);
    }
    
    public void dispose() {
        this.unbind();
        GL30.glDeleteProgram(this.programHandle);
    }
    
    
    public void setShader(String shaderAlias, AShader shader) {
        this.shaders.put(shaderAlias, shader);
    }
    
    public void setFragmentShader(FragmentShader shader) {
        this.setShader(FRAGMENT_SHADER, shader);
    }
    
    public void setVertexShader(VertexShader shader) {
        this.setShader(VERTEX_SHADER, shader);
    }
    
    public AShader getShader(String shaderAlias) {
        return this.shaders.get(shaderAlias);
    }
}
