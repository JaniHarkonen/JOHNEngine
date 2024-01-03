package johnengine.basic.renderer.asset;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.assets.ITexture;
import johnengine.basic.renderer.asset.Mesh.Data;

public class TextureGL implements ITexture<Integer>, IBindable {
    public static final int TARGET = GL30.GL_TEXTURE_2D;
    public static Data DEFAULT_DATA = null;
    
    public static void generateDefault() {
        TextureGL textureGraphics = new TextureGL(Texture.DEFAULT_INSTANCE);
        textureGraphics.generate();
    }

    private final Texture texture;
    private int handle;
    
    public TextureGL(IRendererAsset texture) {
        this.texture = (Texture) texture;
        this.texture.graphics = this;
        this.handle = 0;
    }
    
    public TextureGL() {
        this.texture = null;
        this.handle = 0;
    }
    
    
    @Override
    public IGraphicsAsset<Integer> createInstance(IRendererAsset asset) {
        return new TextureGL(asset);
    }
    
    @Override
    public boolean generate() {
        Texture.Data data = this.texture.getDataDirect();
        
        this.handle = GL30.glGenTextures();
        GL30.glBindTexture(TARGET, this.handle);
        GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1);
        GL30.glTexParameteri(TARGET, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
        GL30.glTexParameteri(TARGET, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
        GL30.glTexImage2D(
            TARGET, 
            0, 
            GL30.GL_RGBA, 
            data.getWidth(), 
            data.getHeight(), 
            0, 
            GL30.GL_RGBA, 
            GL30.GL_UNSIGNED_BYTE, 
            data.getPixels()
        );
        GL30.glGenerateMipmap(TARGET);
        this.unbind();
        
        return true;
    }

    @Override
    public boolean bind() {
        GL30.glBindTexture(TARGET, this.handle);
        
        return true;
    }

    @Override
    public boolean unbind() {
        GL30.glBindTexture(TARGET, 0);
        return true;
    }

    @Override
    public boolean dispose() {
        GL30.glDeleteTextures(this.handle);
        return true;
    }

    @Override
    public Integer getData() {
        return this.handle;
    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }
}
