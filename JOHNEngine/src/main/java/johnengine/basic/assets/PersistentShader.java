package johnengine.basic.assets;

import org.lwjgl.opengl.GL30;

import johnengine.core.renderer.shdprog.Shader;

public class PersistentShader extends Shader {

    public PersistentShader(int type, String source) {
        super(null, null, type);
        this.loaded();
        this.asset = source;
    }
    
    
    @Override
    public void deload() {
            // Persistent shaders cannot be deloaded, only detached
        GL30.glDetachShader(this.shaderProgram, this.handle);
    }
}
