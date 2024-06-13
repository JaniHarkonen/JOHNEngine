package johnengine.basic.assets.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import johnengine.Defaults;
import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.core.assetmngr.asset.ALoadTask;
import johnengine.core.logger.Logger;

public class Texture implements IGraphicsAsset {

    /********************** LoadTask-class **********************/
    
    public static class LoadTask extends ALoadTask {
        private final IGraphicsStrategy graphics;
        private Texture targetAsset;
        
        public LoadTask(IGraphicsStrategy graphics, Texture targetAsset) {
            this.graphics = graphics;
            this.targetAsset = targetAsset;
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
                
                this.targetAsset.info = new Texture.Info(
                    imageBuffer, 
                    widthBuffer.get(), 
                    heightBuffer.get()
                );
                this.graphics.loaded();
            }
        }
    }
    
    
    /********************** Data-class **********************/
    
    public static class Info {
        
        private static class Data {
            private ByteBuffer pixels;
            private int width;
            private int height;
            private boolean repeatHorizontally;
            private boolean repeatVertically;
            
            private Data(ByteBuffer pixels, int width, int height) {
                this.pixels = pixels;
                this.width = width;
                this.height = height;
                this.repeatHorizontally = true;
                this.repeatVertically = true;
            }
        }
        
        
        private Data data;
        
        public Info(ByteBuffer pixels, int width, int height) {
            this.data = new Data(pixels, width, height);
        }
        
        
        public ByteBuffer getPixels() {
            return this.data.pixels;
        }
        
        public byte getPixelAt(int x, int y) {
            if( x >= 0 && x < this.data.width && y >= 0 && y < this.data.height )
            return this.data.pixels.get(y * this.data.width + x);
            
            return 0;
        }
        
        public int getWidth() {
            return this.data.width;
        }
        
        public int getHeight() {
            return this.data.height;
        }
        
        public boolean repeatHorizontally() {
            return this.data.repeatHorizontally;
        }
        
        public boolean repeatVertically() {
            return this.data.repeatVertically;
        }
    }
    
    
    /********************** Texture-class **********************/
    
    private ITextureGraphics graphicsStrategy;
    private Texture.Info info;
    private String name;
    
    public Texture(String name, Texture.Info preloadedInfo, ITextureGraphics graphicsStrategy) {
        this.name = name;
        this.info = preloadedInfo;
        this.graphicsStrategy = graphicsStrategy;
        
        if( this.info == null )
        {
            Logger.log(
                Logger.VERBOSITY_MINIMAL, 
                Logger.SEVERITY_FATAL, 
                this, 
                "Trying to create a texutre with null info!"
            );
        }
    }
    
    public Texture(String name) {
        this.name = name;
        this.graphicsStrategy = null;
        this.info = Defaults.DEFAULT_TEXTURE_INFO;
    }
    
    @Override
    public void deload() {
        this.graphicsStrategy.deload();
    }
    
    
    /********************** SETTERS **********************/
    
    @Override
    public void setGraphicsStrategy(IGraphicsStrategy graphicsStrategy) {
        this.graphicsStrategy = (ITextureGraphics) graphicsStrategy;
    }
    
    
    /********************** GETTERS **********************/

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ITextureGraphics getGraphicsStrategy() {
        return this.graphicsStrategy;
    }
    
    public Texture.Info getInfo() {
        return this.info;
    }
}
