package johnengine.basic.opengl.renderer.asset;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.basic.opengl.renderer.asset.Mesh.Data;

public class TextureGL implements IGraphicsStrategyGL, IBindable {
    public static final int TARGET = GL46.GL_TEXTURE_2D;
    public static Data DEFAULT_DATA = null;
    
    public static void generateDefault(RendererGL renderer) {
        TextureGL textureGraphics = new TextureGL(renderer, Texture.DEFAULT_INSTANCE);
        textureGraphics.generate();
    }

    private final RendererGL renderer;
    private Texture texture;
    private int handle;
    
    public TextureGL(RendererGL renderer, Texture texture) {
        this.texture = texture;
        this.renderer = renderer;
        this.handle = 0;
    }
    
    public TextureGL(RendererGL renderer) {
        this(renderer, null);
    }
    
    
    @Override
    public void loaded() {
        this.renderer.getGraphicsAssetProcessor().generateGraphics(this);
    }
    
    @Override
    public boolean generate() {
        Texture.Data asset = this.texture.getUnsafe();
        
        this.handle = GL46.glGenTextures();
        GL46.glBindTexture(TARGET, this.handle);
        GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1);
        GL46.glTexParameteri(TARGET, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
        GL46.glTexParameteri(TARGET, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
        GL46.glTexImage2D(
            TARGET, 
            0, 
            GL46.GL_RGBA, 
            asset.getWidth(), 
            asset.getHeight(), 
            0, 
            GL46.GL_RGBA, 
            GL46.GL_UNSIGNED_BYTE, 
            asset.getPixels()
        );
        GL46.glGenerateMipmap(TARGET);
        this.unbind();
        this.texture.setGraphics(this);
        
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
    public void deload() {
        this.renderer.getGraphicsAssetProcessor().disposeGraphics(this);
    }
    
    @Override
    public boolean dispose() {
        GL46.glDeleteTextures(this.handle);
        return true;
    }

    public int getHandle() {
        return this.handle;
    }
    
    public Texture getTexture() {
        return this.texture;
    }
}
