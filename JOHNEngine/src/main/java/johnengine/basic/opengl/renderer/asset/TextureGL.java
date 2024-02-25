package johnengine.basic.opengl.renderer.asset;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.assets.ITexture;
import johnengine.basic.opengl.renderer.asset.Mesh.Data;

public class TextureGL implements ITexture<Integer>, IBindable {
    public static final int TARGET = GL46.GL_TEXTURE_2D;
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
        
        this.handle = GL46.glGenTextures();
        GL46.glBindTexture(TARGET, this.handle);
        GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1);
        GL46.glTexParameteri(TARGET, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
        GL46.glTexParameteri(TARGET, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
        GL46.glTexImage2D(
            TARGET, 
            0, 
            GL46.GL_RGBA, 
            data.getWidth(), 
            data.getHeight(), 
            0, 
            GL46.GL_RGBA, 
            GL46.GL_UNSIGNED_BYTE, 
            data.getPixels()
        );
        GL46.glGenerateMipmap(TARGET);
        this.unbind();
        
        return true;
    }

    @Override
    public boolean bind() {
        GL46.glBindTexture(TARGET, this.handle);
        return true;
    }

    @Override
    public boolean unbind() {
        GL46.glBindTexture(TARGET, 0);
        return true;
    }

    @Override
    public boolean dispose() {
        GL46.glDeleteTextures(this.handle);
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
