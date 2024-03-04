package johnengine.basic.assets.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.assetmngr.asset.ALoadTask;

public class Texture extends AAsset<Texture.Data> {

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
                
                this.targetAsset.setAsset(new Data(
                    imageBuffer, 
                    widthBuffer.get(), 
                    heightBuffer.get()
                ));
                
                this.graphics.loaded();
            }
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
    
    public static Texture DEFAULT_INSTANCE;
    
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
        
        Texture.Data asset = new Texture.Data(pixels, width, height);
        DEFAULT_INSTANCE = new Texture("default-texture", true, asset);
    }
    

    /********************** Class body **********************/
    
    private IGraphicsStrategy graphics;
    
    public Texture(String name, boolean isPersistent, Texture.Data preloadedData) {
        super(name, isPersistent, preloadedData);
        this.graphics = DEFAULT_INSTANCE.getGraphics();
    }
    
    public Texture(String name) {
        super(name);
        this.graphics = DEFAULT_INSTANCE.getGraphics();
    }
    
    /*public Texture(String name) {
        this(name, false, DEFAULT_INSTANCE.get());
    }*/
    
    @Override
    protected void deloadImpl() {
        this.graphics.deload();
        this.graphics = DEFAULT_INSTANCE.getGraphics();
    }
    
    
    /********************** SETTERS **********************/
    
    public void setGraphics(IGraphicsStrategy graphics) {
        this.graphics = graphics;
    }
    
    
    /********************** GETTERS **********************/
    
    @Override
    public Texture.Data getDefault() {
        return DEFAULT_INSTANCE.get();
    }
    
    public IGraphicsStrategy getDefaultGraphics() {
        return DEFAULT_INSTANCE.getGraphics();
    }
    
    public IGraphicsStrategy getGraphics() {
        return this.graphics;
    }
    
    /*public Texture.Data getUnsafe() {
        return this.asset;
    }*/
}
