package johnengine.basic.assets.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.core.assetmngr.asset.ALoadTask;
import johnengine.core.exception.JOHNException;

public class Texture implements IGraphicsAsset {
    
    /********************** MissingTextureInfoException-class **********************/
    
    @SuppressWarnings("serial")
    public static class MissingTextureInfoException extends JOHNException {
        public MissingTextureInfoException(
            String message, 
            Texture texture) 
        {
            super(message, "%textureName", texture.getName(), "%textureInstance", texture);
        }
    }

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
            
            private Data(ByteBuffer pixels, int width, int height) {
                this.pixels = pixels;
                this.width = width;
                this.height = height;
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
    }
    
    
    /********************** Texture-class **********************/
    
    public static Texture DEFAULT_INSTANCE;
    public static Texture.Info DEFAULT_TEXTURE_INFO = new Texture.Info(null, 0, 0);
    
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
        
        DEFAULT_TEXTURE_INFO.data = new Texture.Info.Data(pixels, width, height);
    }
    

    /********************** Class body **********************/
    
    private IGraphicsStrategy graphicsStrategy;
    private Texture.Info info;
    private String name;
    
    public Texture(String name, Texture.Info preloadedInfo, IGraphicsStrategy graphicsStrategy) {
        this.name = name;
        this.info = preloadedInfo;
        this.graphicsStrategy = graphicsStrategy;
        
        if( this.info == null )
        {
            throw new MissingTextureInfoException(
                "Trying to create texture '%textureName' with null info!" +
                "\n Texture instance: %textureInstance",
                this
            );
        }
    }
    
    public Texture(String name) {
        this.name = name;
        this.graphicsStrategy = null;
        this.info = Texture.DEFAULT_TEXTURE_INFO;
    }
    
    @Override
    public void deload() {
        this.graphicsStrategy.deload();
    }
    
    
    /********************** SETTERS **********************/
    
    @Override
    public void setGraphicsStrategy(IGraphicsStrategy graphicsStrategy) {
        this.graphicsStrategy = graphicsStrategy;
    }
    
    
    /********************** GETTERS **********************/

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IGraphicsStrategy getGraphicsStrategy() {
        return this.graphicsStrategy;
    }
    
    public Texture.Info getInfo() {
        return this.info;
    }
}
