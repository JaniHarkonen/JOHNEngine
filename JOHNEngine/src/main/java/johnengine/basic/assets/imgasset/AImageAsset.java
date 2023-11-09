package johnengine.basic.assets.imgasset;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import johnengine.basic.renderer.asset.ARendererAsset;
import johnengine.core.assetmngr.asset.AAssetLoader;
import johnengine.testing.DebugUtils;

public abstract class AImageAsset extends ARendererAsset<AImageAsset.Data> {
    
    public static class Data {
        private final ByteBuffer buffer;
        private final int width;
        private final int height;
        
        public Data(ByteBuffer buffer, int width, int height) {
            this.buffer = buffer;
            this.width = width;
            this.height = height;
        }
        
        
        public ByteBuffer getPixels() {
            return this.buffer;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
    }
    
    public static class Loader extends AAssetLoader {
        private AImageAsset targetAsset;
        
        public Loader(String path, AImageAsset targetAsset) {
            super(path);
            this.targetAsset = targetAsset;
        }
        
        public Loader(AImageAsset targetAsset) {
            this(null, targetAsset);
        }
        
        public Loader() {
            this(null, null);
        }
        

        @Override
        protected void loadImpl() {
            try( MemoryStack memoryStack = MemoryStack.stackPush() )
            {
                IntBuffer widthBuffer = memoryStack.mallocInt(1);
                IntBuffer heightBuffer = memoryStack.mallocInt(1);
                IntBuffer channelCountBuffer = memoryStack.mallocInt(1);
                
                ByteBuffer imageBuffer = STBImage.stbi_load(
                    this.getPath(), 
                    widthBuffer, 
                    heightBuffer, 
                    channelCountBuffer, 
                    4
                );
                
                this.targetAsset.setAsset(new AImageAsset.Data(
                    imageBuffer, 
                    widthBuffer.get(), 
                    heightBuffer.get()
                ));
                DebugUtils.log(this, "loaded :)");
            }
        }
    }
    
    
    /************************ Texture-class ************************/

    public AImageAsset(String name, boolean isPersistent) {
        super(name, isPersistent);
    }
    
    public AImageAsset(String name) {
        this(name, false);
    }

    @Override
    protected void deloadImpl() {
        if( this.asset != null )
        STBImage.stbi_image_free(this.asset.buffer);
    }
    
    
    public ByteBuffer getPixels() {
        return this.get().getPixels();
    }
    
    public int getWidth() {
        return this.get().getWidth();
    }
    
    public int getHeight() {
        return this.get().getHeight();
    }
}
