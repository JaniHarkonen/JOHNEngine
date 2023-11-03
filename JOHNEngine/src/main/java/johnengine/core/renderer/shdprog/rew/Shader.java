package johnengine.core.renderer.shdprog.rew;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.rew.textasset.TextAsset;

public class Shader extends TextAsset {
    
    public static class Loader extends TextAsset.Loader {
        public boolean poll(Shader targetAsset) {
            if( !this.isLoaded() )
            return false;
            
            targetAsset.asset = this.text;
            return true;            
        }
    }
    
    protected final int type;
    
    protected int shaderProgram;
    protected int handle;

    public Shader(int type, String name, boolean isPersistent, String preloadedAsset) {
        super(name, isPersistent, preloadedAsset);
        this.type = type;
    }
    
    public Shader(int type, String name) {
        this(type, name, false, null);
    }
    
    
    public boolean attach(int shaderProgram) {
        return this.attach(shaderProgram, true);
    }
    
    public boolean attach(int shaderProgram, boolean allowBlocking) {
        this.handle = GL30.glCreateShader(this.type);
        
            // Block until the shader source code is loaded (if blocking is allowed)
        while( allowBlocking )
        ;
        
            // Don't attach if the source code has not loaded
        if( this.asset == null )
        return false;
        
        GL30.glShaderSource(this.handle, this.asset);
        GL30.glCompileShader(this.handle);
        GL30.glAttachShader(this.shaderProgram, this.handle);
        this.shaderProgram = shaderProgram;

        return true;
    }
    
    public void detach() {
        GL30.glDetachShader(this.shaderProgram, this.handle);
        GL30.glDeleteShader(this.handle);
        this.handle = 0;
        this.shaderProgram = 0;
    }
}
