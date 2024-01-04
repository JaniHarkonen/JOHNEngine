package johnengine.basic.renderer.asset;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IGeneratable;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.renderer.ShaderProgram;
import johnengine.testing.DebugUtils;

public final class Shader extends TextAsset implements IGeneratable {
    
    public static class ShaderException extends RuntimeException {
        public ShaderException(String message) {
            super(message);
        }
        
        public ShaderException(String message, String shaderName, String reason) {
            this(
                message
                .replaceAll("%shader", shaderName)
                .replaceAll("%reason", reason)
            );
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
        if( this.asset == null )
        return false;
        
        this.handle = GL30.glCreateShader(this.type);
        GL46.glShaderSource(this.handle, this.asset);
        GL46.glCompileShader(this.handle);
        
            // Failed to compile
        if( GL30.glGetShaderi(this.handle, GL46.GL_COMPILE_STATUS) != GL30.GL_TRUE )
        {
            DebugUtils.log(this, GL30.glGetShaderInfoLog(this.handle));
            //throw new ShaderException("Failed to compile a shader '%shader'!", this.name);
            throw (new ShaderException(
                "Failed to compile shader '%shader'!\n" +
                "Reason: %reason",
                this.name,
                GL30.glGetShaderInfoLog(this.handle)
            ));
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
        
        GL30.glAttachShader(shaderProgram.getHandle(), this.handle);
        return true;
    }
    
    public void detachFrom(ShaderProgram shaderProgram) {
        GL30.glDetachShader(shaderProgram.getHandle(), this.handle);
    }
    
    @Override
    public boolean dispose() {
        GL30.glDeleteShader(this.handle);
        this.handle = 0;
        
        return true;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
