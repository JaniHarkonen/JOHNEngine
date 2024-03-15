package johnengine.basic.opengl.renderer.asset;

import org.lwjgl.opengl.GL46;

import johnengine.Defaults;
import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.opengl.renderer.RendererGL;

public class TextureGraphicsGL extends AGraphicsStrategyGL<TextureGraphicsGL.TextureHandle> implements IBindable {
    
    
    /********************* TextureHandle-class *********************/
    
    public static class TextureHandle {
        private class Handle {
            private int value;
            
            private Handle(int handleValue) {
                this.value = handleValue;
            }
            
            private Handle() {
                this(0);
            }
        }
        
        private Handle handle;
        
        public TextureHandle() {
            this.handle = new Handle();
        }
        
        public TextureHandle(int handleValue) {
            this.handle = new Handle(handleValue);
        }
        
        
        public int get() {
            return this.handle.value;
        }
    }
    
    
    /********************* TextureGraphicsGL-class *********************/
    
    public static final int TARGET = GL46.GL_TEXTURE_2D;
    public static final TextureHandle DEFAULT_TEXTURE_HANDLE = new TextureHandle();
    
    public static void generateDefault(RendererGL renderer) {
        //DEFAULT_TEXTURE_HANDLE.handle.value = generateTexture(Texture.DEFAULT_TEXTURE_INFO);
        DEFAULT_TEXTURE_HANDLE.handle.value = generateTexture(Defaults.DEFAULT_TEXTURE_INFO);
        Defaults.DEFAULT_TEXTURE.setGraphicsStrategy(new TextureGraphicsGL(renderer, Defaults.DEFAULT_TEXTURE, true));
    }
    
    private static int generateTexture(Texture.Info textureInfo) {
        int handle = GL46.glGenTextures();
        
        bindTexture(handle);
        GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1);
        GL46.glTexParameteri(TARGET, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
        GL46.glTexParameteri(TARGET, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
        GL46.glTexImage2D(
            TARGET, 
            0, 
            GL46.GL_RGBA, 
            textureInfo.getWidth(), 
            textureInfo.getHeight(), 
            0, 
            GL46.GL_RGBA, 
            GL46.GL_UNSIGNED_BYTE, 
            textureInfo.getPixels()
        );
        GL46.glGenerateMipmap(TARGET);
        unbindTexture();
        
        return handle;
    }
    
    private static void bindTexture(int handle) {
        GL46.glBindTexture(TARGET, handle);
    }
    
    private static void unbindTexture() {
        GL46.glBindTexture(TARGET, 0);
    }

    
    /********************* Class body *********************/
    
    private Texture texture;
    private TextureGraphicsGL.TextureHandle handle;
    
    public TextureGraphicsGL(RendererGL renderer, Texture texture, boolean isPersistent) {
        super(renderer, isPersistent);
        this.texture = texture;
        this.handle = TextureGraphicsGL.DEFAULT_TEXTURE_HANDLE;
    }
    
    public TextureGraphicsGL(RendererGL renderer) {
        this(renderer, null, false);
    }
    
    
    @Override
    public void loaded() {
        this.renderer.getGraphicsAssetProcessor().generateGraphics(this);
    }
    
    @Override
    public boolean generate() {
        this.handle = new TextureGraphicsGL.TextureHandle(generateTexture(this.texture.getInfo()));
        this.texture.setGraphicsStrategy(this);
        
        return true;
    }

    @Override
    public boolean bind() {
        bindTexture(this.handle.get());
        return true;
    }

    @Override
    public boolean unbind() {
        unbindTexture();
        return true;
    }

    @Override
    public void deload() {
        this.renderer.getGraphicsAssetProcessor().disposeGraphics(this);
    }
    
    @Override
    public boolean dispose() {
        GL46.glDeleteTextures(this.handle.get());
        return true;
    }

    public int getHandle() {
        return this.handle.get();
    }
    
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    protected void deloadImpl() {
        if( this.handle != TextureGraphicsGL.DEFAULT_TEXTURE_HANDLE )
        this.dispose();
    }

    @Override
    public TextureGraphicsGL.TextureHandle getDefaultGraphics() {
        return TextureGraphicsGL.DEFAULT_TEXTURE_HANDLE;
    }
}
