package johnengine.basic.assets.rew.imgasset;

import org.lwjgl.opengl.GL30;

import johnengine.core.renderer.ARenderer;

public class Texture extends AImageAsset {

    private int handle;
    
    public Texture(String name, boolean isPersistent) {
        super(name, isPersistent);
        this.handle = 0;
    }
    
    public Texture(String name) {
        this(name, false);
    }

    @Override
    public void render(ARenderer renderer) {
        Data textureData = this.get();
        int target = GL30.GL_TEXTURE_2D;
        
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        if( this.handle == 0 && this.asset != null )
        {
            this.handle = GL30.glGenTextures();
            GL30.glBindTexture(target, this.handle);
            GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1);
            GL30.glTexParameteri(target, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
            GL30.glTexParameteri(target, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
            GL30.glTexImage2D(
                target,
                0, 
                GL30.GL_RGBA, 
                textureData.getWidth(), 
                textureData.getHeight(), 
                0, 
                GL30.GL_RGBA, 
                GL30.GL_UNSIGNED_BYTE, 
                textureData.getPixels()
            );
            GL30.glGenerateMipmap(target);
        }
        else
        GL30.glBindTexture(target, this.handle);
    }

    @Override
    protected void deloadImpl() {
        super.deloadImpl();
        
        if( this.handle != 0 )
        GL30.glDeleteTextures(this.handle);
    }
}
