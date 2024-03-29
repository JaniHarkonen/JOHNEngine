package johnengine.basic.opengl.renderer.asset;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IGeneratable;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.core.exception.JOHNException;

public final class Shader extends TextAsset implements IGeneratable {
    
    @SuppressWarnings("serial")
    public static class ShaderException extends JOHNException {

        public ShaderException(String message, String shaderName, String reason) {
            super(message, "%shader", shaderName, "%reason", "\n" + reason);
        }
    }
    
    protected final int type;
    protected int handle;
    
    public Shader(int type, String name, boolean isPersistent, String preloadedAsset) {
        super(name, isPersistent, preloadedAsset);
        this.type = type;
    }
    
    public Shader(int type, String name) {
        this(type, name, false, null);
    }
    
    
    @Override
    public boolean generate() {
        if( this.asset.isNull() )
        return false;
        
        this.handle = GL46.glCreateShader(this.type);
        GL46.glShaderSource(this.handle, this.asset.get());
        GL46.glCompileShader(this.handle);
        
            // Failed to compile
        if( GL46.glGetShaderi(this.handle, GL46.GL_COMPILE_STATUS) != GL46.GL_TRUE )
        {
            throw new ShaderException(
                "Failed to compile shader '%shader'!\n" +
                "Reason: %reason",
                this.name,
                GL46.glGetShaderInfoLog(this.handle)
            );
        }
        
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
        
        GL46.glAttachShader(shaderProgram.getHandle(), this.handle);
        return true;
    }
    
    public void detachFrom(ShaderProgram shaderProgram) {
        GL46.glDetachShader(shaderProgram.getHandle(), this.handle);
    }
    
    @Override
    public boolean dispose() {
        GL46.glDeleteShader(this.handle);
        this.handle = 0;
        
        return true;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
