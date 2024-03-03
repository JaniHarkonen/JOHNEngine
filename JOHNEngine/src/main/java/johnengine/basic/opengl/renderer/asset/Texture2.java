package johnengine.basic.opengl.renderer.asset;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.assets.ITexture;
import johnengine.core.assetmngr.asset.ALoadTask;
import johnengine.core.assetmngr.asset.ILoaderMonitor;

public class Texture2 extends ARendererAsset<ITexture<?>, Texture2.Data> {

    /********************** LoadTask-class **********************/
    
    public static class LoadTask extends ALoadTask {
        private Texture2 targetAsset;
        private ILoaderMonitor<IRendererAsset> monitor;
        
        public LoadTask(Texture2 targetAsset) {
            this.targetAsset = targetAsset;
            this.monitor = null;
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
                
                this.targetAsset.data = new Data(
                    imageBuffer, 
                    widthBuffer.get(), 
                    heightBuffer.get()
                );
                
                if( this.monitor != null )
                this.monitor.assetLoaded(this.targetAsset);
            }
        }
        
        
        public void setMonitor(ILoaderMonitor<IRendererAsset> monitor) {
            this.monitor = monitor;
        }
    }
    
    
    /********************** Data-class **********************/
    
    public static class Data {
        private ByteBuffer pixels;
        private int width;
        private int height;
        
        public Data(ByteBuffer pixels, int width, int height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
        }
        
        
        public ByteBuffer getPixels() {
            return this.pixels;
        }
        
        public byte getPixelAt(int x, int y) {
            if( x >= 0 && x < this.width && y >= 0 && y < this.height )
            return this.pixels.get(y * this.width + x);
            
            return 0;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
    }
    
    
    /********************** Texture-class **********************/
    
    public static Texture2 DEFAULT_INSTANCE;
    
    static {
            // T_T
        String bytes = 
            "0000000000000000" + 
            "0000000000000000" + 
            "0000000000000000" + 
            "0111110000111110" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0000011111100000" + 
            "0000000000000000" + 
            "0000000000000000" + 
            "0000000000000000";
        
            // WTF
        /*
        String bytes = 
            "0001000000001000" + 
            "0001000110001000" + 
            "0000101001010000" + 
            "0000010000100000" + 
            "0000000000000000" + 
            "0000011111110000" + 
            "0000000010000000" + 
            "0000000010000000" + 
            "0000000010000000" + 
            "0000000010000000" + 
            "0000000000000000" + 
            "0000011111100000" + 
            "0000000000100000" + 
            "0000000111100000" + 
            "0000000000100000" + 
            "0000000000100000";
          */  
        
        int width = 16;
        int height = 16;
        int channelCount = 4;
        ByteBuffer pixels = MemoryUtil.memAlloc(bytes.length() * channelCount);
        
            // Populate pixels upside-down due to OpenGL
        for( int i = bytes.length() - 1; i >= 0; i-- )
        {
            byte value = (bytes.charAt(i) == '1') ? (byte) 255 : 0;
            
            pixels.put((byte) value);
            pixels.put((byte) value);
            pixels.put((byte) value);
            pixels.put((byte) 255);
        }
        
        pixels.flip();
        
        DEFAULT_INSTANCE = new Texture2("default-texture", true);
        DEFAULT_INSTANCE.data = new Data(pixels, width, height);
    }
    

    public Texture2(String name, boolean isPersistent) {
        super(name, isPersistent);
    }
    
    public Texture2(String name) {
        this(name, false);
    }
    
    
    @Override
    public Data getDefault() {
        return DEFAULT_INSTANCE.data;
    }
    
    @Override
    public ITexture<?> getDefaultGraphics() {
        return DEFAULT_INSTANCE.graphics;
    }
}
