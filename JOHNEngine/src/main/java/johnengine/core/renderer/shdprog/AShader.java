package johnengine.core.renderer.shdprog;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.TextAsset;

public abstract class AShader extends TextAsset {

    private final int type;
    
    private int shaderProgram;
    private int handle;
    
    protected AShader(String name, String relativePath) {
        super(name, relativePath);
        this.type = this.getType();
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
    
    public void deload() {
        GL30.glDetachShader(this.shaderProgram, this.handle);
        GL30.glDeleteShader(this.handle);
        this.handle = 0;
        this.shaderProgram = 0;
    }
    
    public abstract int getType();
}
