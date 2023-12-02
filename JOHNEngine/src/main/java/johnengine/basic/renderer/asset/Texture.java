package johnengine.basic.renderer.asset;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import johnengine.basic.assets.ITexture;
import johnengine.core.assetmngr.asset.AAssetLoader;
import johnengine.testing.DebugUtils;

public class Texture extends ARendererAsset<ITexture<?>, Texture.Data> {

    /********************** Loader-class **********************/
    
    public static class Loader extends AAssetLoader {
        private Texture targetAsset;
        
        public Loader(Texture targetAsset) {
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
                
                this.targetAsset.data = new Data(
                    imageBuffer, 
                    widthBuffer.get(), 
                    heightBuffer.get()
                );
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
    
    public static final int TARGET = GL30.GL_TEXTURE_2D;
    public static Texture DEFAULT_INSTANCE;
    
    static {
            // Generate default image
        byte[] bytes = new byte[] {
            0, 0, 0, -1, 64, 64, 64, -1, -128, -128, -128, 
            -1, -1, 0, 0, -1, 127, 0, 0, -1, -1, 
            106, 0, -1, 127, 51, 0, -1, -1, -40, 0, 
            -1, 127, 106, 0, -1, -74, -1, 0, -1, 91, 
            127, 0, -1, 76, -1, 0, -1, 38, 127, 0, 
            -1, 0, -1, 33, -1, 0, 127, 14, -1, 0, 
            -1, -112, -1, 0, 127, 70, -1, 0, -1, -1, 
            -1, 0, 127, 127, -1, 0, -108, -1, -1, 0, 
            74, 127, -1, 0, 38, -1, -1, 0, 19, 127, 
            -1, 72, 0, -1, -1, 33, 0, 127, -1, -78, 
            0, -1, -1, 87, 0, 127, -1, -1, 0, -36, 
            -1, 127, 0, 110, -1, -1, 0, 110, -1, 127, 
            0, 55, -1, -96, -96, -96, -1, 48, 48, 48, 
            -1, -1, 127, 127, -1, -1, -78, 127, -1, -1, 
            -23, 127, -1, -38, -1, 127, -1, -91, -1, 127, 
            -1, 127, -1, -114, -1, 127, -1, -59, -1, 127, 
            -1, -1, -1, 127, -55, -1, -1, 127, -110, -1, 
            -1, -95, 127, -1, -1, -42, 127, -1, -1, -1, 
            127, -19, -1, -1, 127, -74, -1, -1, -1, -1, 
            -1, 0, 0, 0, -1, 64, 64, 64, -1, -128, 
            -128, -128, -1, -1, 0, 0, -1, 127, 0, 0, 
            -1, -1, 106, 0, -1, 127, 51, 0, -1, -1, 
            -40, 0, -1, 127, 106, 0, -1, -74, -1, 0, 
            -1, 91, 127, 0, -1, 76, -1, 0, -1, 38, 
            127, 0, -1, 0, -1, 33, -1, 0, 127, 14, 
            -1, 0, -1, -112, -1, 0, 127, 70, -1, 0, 
            -1, -1, -1, 0, 127, 127, -1, 0, -108, -1, 
            -1, 0, 74, 127, -1, 0, 38, -1, -1, 0, 
            19, 127, -1, 72, 0, -1, -1, 33, 0, 127, 
            -1, -78, 0, -1, -1, 87, 0, 127, -1, -1, 
            0, -36, -1, 127, 0, 110, -1, -1, 0, 110, 
            -1, 127, 0, 55, -1, -96, -96, -96, -1, 48, 
            48, 48, -1, -1, 127, 127, -1, -1, -78, 127, 
            -1, -1, -23, 127, -1, -38, -1, 127, -1, -91, 
            -1, 127, -1, 127, -1, -114, -1, 127, -1, -59, 
            -1, 127, -1, -1, -1, 127, -55, -1, -1, 127, 
            -110, -1, -1, -95, 127, -1, -1, -42, 127, -1, 
            -1, -1, 127, -19, -1, -1, 127, -74, -1, -1, 
            -1, -1, -1, 0, 0, 0, -1, 64, 64, 64, 
            -1, -128, -128, -128, -1, -1, 0, 0, -1, 127, 
            0, 0, -1, -1, 106, 0, -1, 127, 51, 0, 
            -1, -1, -40, 0, -1, 127, 106, 0, -1, -74, 
            -1, 0, -1, 91, 127, 0, -1, 76, -1, 0, 
            -1, 38, 127, 0, -1, 0, -1, 33, -1, 0, 
            127, 14, -1, 0, -1, -112, -1, 0, 127, 70, 
            -1, 0, -1, -1, -1, 0, 127, 127, -1, 0, 
            -108, -1, -1, 0, 74, 127, -1, 0, 38, -1, 
            -1, 0, 19, 127, -1, 72, 0, -1, -1, 33, 
            0, 127, -1, -78, 0, -1, -1, 87, 0, 127, 
            -1, -1, 0, -36, -1, 127, 0, 110, -1, -1, 
            0, 110, -1, 127, 0, 55, -1, -96, -96, -96, 
            -1, 48, 48, 48, -1, -1, 127, 127, -1, -1, 
            -78, 127, -1, -1, -23, 127, -1, -38, -1, 127, 
            -1, -91, -1, 127, -1, 127, -1, -114, -1, 127, 
            -1, -59, -1, 127, -1, -1, -1, 127, -55, -1, 
            -1, 127, -110, -1, -1, -95, 127, -1, -1, -42, 
            127, -1, -1, -1, 127, -19, -1, -1, 127, -74, 
            -1, -1, -1, -1, -1, 0, 0, 0, -1, 64, 
            64, 64, -1, -128, -128, -128, -1, -1, 0, 0, 
            -1, 127, 0, 0, -1, -1, 106, 0, -1, 127, 
            51, 0, -1, -1, -40, 0, -1, 127, 106, 0, 
            -1, -74, -1, 0, -1, 91, 127, 0, -1, 76, 
            -1, 0, -1, 38, 127, 0, -1, 0, -1, 33, 
            -1, 0, 127, 14, -1, 0, -1, -112, -1, 0, 
            127, 70, -1, 0, -1, -1, -1, 0, 127, 127, 
            -1, 0, -108, -1, -1, 0, 74, 127, -1, 0, 
            38, -1, -1, 0, 19, 127, -1, 72, 0, -1, 
            -1, 33, 0, 127, -1, -78, 0, -1, -1, 87, 
            0, 127, -1, -1, 0, -36, -1, 127, 0, 110, 
            -1, -1, 0, 110, -1, 127, 0, 55, -1, -96, 
            -96, -96, -1, 48, 48, 48, -1, -1, 127, 127, 
            -1, -1, -78, 127, -1, -1, -23, 127, -1, -38, 
            -1, 127, -1, -91, -1, 127, -1, 127, -1, -114, 
            -1, 127, -1, -59, -1, 127, -1, -1, -1, 127, 
            -55, -1, -1, 127, -110, -1, -1, -95, 127, -1, 
            -1, -42, 127, -1, -1, -1, 127, -19, -1, -1, 
            127, -74, -1, -1, -1, -1, -1, 0, 0, 0, 
            -1, 64, 64, 64, -1, -128, -128, -128, -1, -1, 
            0, 0, -1, 127, 0, 0, -1, -1, 106, 0, 
            -1, 127, 51, 0, -1, -1, -40, 0, -1, 127, 
            106, 0, -1, -74, -1, 0, -1, 91, 127, 0, 
            -1, 76, -1, 0, -1, 38, 127, 0, -1, 0, 
            -1, 33, -1, 0, 127, 14, -1, 0, -1, -112, 
            -1, 0, 127, 70, -1, 0, -1, -1, -1, 0, 
            127, 127, -1, 0, -108, -1, -1, 0, 74, 127, 
            -1, 0, 38, -1, -1, 0, 19, 127, -1, 72, 
            0, -1, -1, 33, 0, 127, -1, -78, 0, -1, 
            -1, 87, 0, 127, -1, -1, 0, -36, -1, 127, 
            0, 110, -1, -1, 0, 110, -1, 127, 0, 55, 
            -1, -96, -96, -96, -1, 48, 48, 48, -1, -1, 
            127, 127, -1, -1, -78, 127, -1, -1, -23, 127, 
            -1, -38, -1, 127, -1, -91, -1, 127, -1, 127, 
            -1, -114, -1, 127, -1, -59, -1, 127, -1, -1, 
            -1, 127, -55, -1, -1, 127, -110, -1, -1, -95, 
            127, -1, -1, -42, 127, -1, -1, -1, 127, -19, 
            -1, -1, 127, -74, -1, -1, -1, -1, -1, 0, 
            0, 0, -1, 64, 64, 64, -1, -128, -128, -128, 
            -1, -1, 0, 0, -1, 127, 0, 0, -1, -1, 
            106, 0, -1, 127, 51, 0, -1, -1, -40, 0, 
            -1, 127, 106, 0, -1, -74, -1, 0, -1, 91, 
            127, 0, -1, 76, -1, 0, -1, 38, 127, 0, 
            -1, 0, -1, 33, -1, 0, 127, 14, -1, 0, 
            -1, -112, -1
        };
        int width = 16;
        int height = 16;
        int s = width * height;
        DebugUtils.log(null, "LENGTHIO: ", bytes.length);
        ByteBuffer pixels = ByteBuffer.allocate(bytes.length);
        
        for( int i = 0; i < bytes.length; i++ )
        pixels.put(bytes[i]);
        
        String lol = "";
        for( int i = 0; i < s; i+=4 )
        {
            
        }
        DebugUtils.log(null, "default texture bytes: ", lol);
        
        //pixels.flip();
        try( MemoryStack memoryStack = MemoryStack.stackPush() )
        {
            IntBuffer bw = memoryStack.mallocInt(1);
            IntBuffer bh = memoryStack.mallocInt(1);
            IntBuffer bc = memoryStack.mallocInt(1);
            pixels = STBImage.stbi_load(
                "C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default_texture.png", 
                bw, bh, bc,
                4
            );
            
            width = bw.get();
            height = bh.get();
        }
        
        DEFAULT_INSTANCE = new Texture("default-texture", true);
        DEFAULT_INSTANCE.data = new Data(pixels, width, height);
    }
    

    public Texture(String name, boolean isPersistent) {
        super(name, isPersistent);
    }
    
    public Texture(String name) {
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
