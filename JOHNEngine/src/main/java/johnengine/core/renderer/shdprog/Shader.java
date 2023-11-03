/*package johnengine.core.renderer.shdprog;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.TextAsset;

public class Shader extends TextAsset {

    protected final int type;
    
    protected int shaderProgram;
    protected int handle;
    
    public Shader(String name, String relativePath, int type, boolean isPersistent, String preloadedAsset) {
        super(name, relativePath, isPersistent, preloadedAsset);
        this.type = type;
    }
    
    public Shader(String name, String relativePath, int type) {
        this(name, relativePath, type, false, null);
    }
    
    
    public void attach(int shaderProgram) {
        this.handle = GL30.glCreateShader(this.type);
        
            // Attach once the source code has been loaded in (blocking)
        while( true )
        {
            if( this.isLoaded() )
            {
                GL30.glShaderSource(this.handle, this.asset);
                GL30.glCompileShader(this.handle);
                GL30.glAttachShader(this.shaderProgram, this.handle);
                this.shaderProgram = shaderProgram;
                
                break;
            }
        }
    }
    
    public void detach() {
        GL30.glDetachShader(this.shaderProgram, this.handle);
        GL30.glDeleteShader(this.handle);
        this.handle = 0;
        this.shaderProgram = 0;
    }
}
*/