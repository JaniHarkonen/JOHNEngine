package johnengine.basic.assets.imgasset;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL30;

import johnengine.basic.renderer.Renderer3D;
import johnengine.core.renderer.ARenderer;
import johnengine.testing.DebugUtils;

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
    public AImageAsset.Data getDefault() {
        return Renderer3D.DEFAULT_TEXTURE.asset;
    }

    @Override
    public void render(ARenderer renderer) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        int textureHandle = this.handle;
        
        if( this.asset == null )
        textureHandle = Renderer3D.DEFAULT_TEXTURE.handle;
        
        if( textureHandle == 0 && this.asset != null )
        this.generate();
        else
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureHandle);
    }
    
    public void generate() {
        int target = GL30.GL_TEXTURE_2D;
        Data textureData = this.get();
        
        if( this.asset == null )
        textureData = new AImageAsset.Data(ByteBuffer.wrap(new byte[0]), 0, 0);
        
        this.handle = GL30.glGenTextures();
        DebugUtils.log(this, this.handle);
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

    @Override
    protected void deloadImpl() {
        super.deloadImpl();
        
        if( this.handle != 0 )
        GL30.glDeleteTextures(this.handle);
    }
}
