package johnengine.basic.renderer.asset;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import johnengine.core.assetmngr.asset.AAssetLoader;
import johnengine.core.renderer.ARenderer;
import johnengine.testing.DebugUtils;

public class Texture extends ARendererAsset<Texture.Data> {

    
    /********************** Data-class **********************/
    
    public static class Data extends ARendererAsset.Data {
        private ByteBuffer pixels;
        private int handle;
        private int width;
        private int height;
        
        public Data(ByteBuffer pixels, int width, int height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
            this.handle = 0;
        }
        
        
        @Override
        public void generate() {
            Data data = this;
            
            data.setHandle(GL30.glGenTextures());
            GL30.glBindTexture(TARGET, data.getHandle());
            GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1);
            GL30.glTexParameteri(TARGET, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
            GL30.glTexParameteri(TARGET, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
            
            DebugUtils.log(this, "texture dimensions: ", data.getWidth(), data.getHeight());
            
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
            //GL30.glActiveTexture(GL30.GL_TEXTURE0);
            //GL30.glBindTexture(TARGET, data.getHandle());
        }
        
        @Override
        public void dispose() {
            STBImage.stbi_image_free(this.pixels);
            GL30.glDeleteTextures(this.handle);
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
        
        int getHandle() {
            return this.handle;
        }
        
        
        void setHandle(int handle) {
            this.handle = handle;
        }
    }
    
    
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
                
                this.targetAsset.setAsset(new Texture.Data(
                    imageBuffer, 
                    widthBuffer.get(), 
                    heightBuffer.get()
                ));
            }
        }
    }
    
    
    /********************** Texture-class **********************/
    
    public static final int TARGET = GL30.GL_TEXTURE_2D;
    public static Data DEFAULT_DATA = null;
    
    public static void generateDefault() {
        if( DEFAULT_DATA != null )
        return;
        
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
                "C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\creep.png", 
                bw, bh, bc,
                4
            );
            
            width = bw.get();
            height = bh.get();
        }
        
        DEFAULT_DATA = new Data(pixels, width, height);
        DEFAULT_DATA.generate();
    }
    

    public Texture(String name, boolean isPersistent, ARenderer renderer) {
        super(name, isPersistent, renderer);
    }
    
    public Texture(String name) {
        this(name, false, null);
    }
    
    
    @Override
    public void generate() {
        /*Data data = this.get();
        
        data.setHandle(GL30.glGenTextures());
        GL30.glBindTexture(TARGET, data.getHandle());
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
        this.unbind();*/
    }

    @Override
    public void bind() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(TARGET, this.get().getHandle());
    }

    @Override
    public void unbind() {
        GL30.glBindTexture(TARGET, 0);
    }

    @Override
    protected void deloadImpl() {
        if( this.asset == null )
        return;
        
        this.renderer.disposeAssetData(this.get());
    }
    
    @Override
    public Texture.Data getDefault() {
        return DEFAULT_DATA;
    }
}