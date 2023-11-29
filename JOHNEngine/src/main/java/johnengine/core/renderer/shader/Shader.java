package johnengine.core.renderer.shader;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.textasset.TextAsset;

public final class Shader extends TextAsset {
    protected final int type;
    protected int handle;
    
    public Shader(int type, String name, boolean isPersistent, String preloadedAsset) {
        super(name, isPersistent, preloadedAsset);
        this.type = type;
    }
    
    public Shader(int type, String name) {
        this(type, name, false, null);
    }
    
    
    public boolean generate() {
        if( this.asset == null )
        return false;
        
        this.handle = GL30.glCreateShader(this.type);
        GL30.glShaderSource(this.handle, this.asset);
        GL30.glCompileShader(this.handle);
        return true;
    }
    
    public boolean generateAndAttach(ShaderProgram shaderProgram) {
        return this.attachTo(shaderProgram, true);
    }
    
    public boolean attachTo(ShaderProgram shaderProgram, boolean allowBlocking) {
        
            // Attempt to generate
        while( !this.generate() )
        {
            if( !allowBlocking )
            return false;
        }
        
        GL30.glAttachShader(shaderProgram.getHandle(), this.handle);
        return true;
    }
    
    public void detachFrom(ShaderProgram shaderProgram) {
        GL30.glDetachShader(shaderProgram.getHandle(), this.handle);
    }
    
    public void dispose() {
        GL30.glDeleteShader(this.handle);
        this.handle = 0;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
