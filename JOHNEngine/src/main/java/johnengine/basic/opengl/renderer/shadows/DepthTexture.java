package johnengine.basic.opengl.renderer.shadows;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;

public class DepthTexture implements IGeneratable, IBindable {
    
    private static final int TARGET = GL46.GL_TEXTURE_2D;
    

    private int handle;
    private int width;
    private int height;
    private int pixelFormat;
    
    public DepthTexture(int width, int height, int pixelFormat) {
        this.handle = 0;
        this.width = width;
        this.height = height;
        this.pixelFormat = pixelFormat;
    }

    
    @Override
    public boolean generate() {
        this.handle = GL46.glGenTextures();
        int target = DepthTexture.TARGET;
        
        this.bind();
        
        GL46.glTexImage2D(
            target, 
            0, 
            GL46.GL_DEPTH_COMPONENT, 
            this.width, 
            this.height, 
            0, 
            this.pixelFormat, 
            GL46.GL_FLOAT, 
            (ByteBuffer) null
        );
        
        GL46.glTexParameteri(target, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_LINEAR);
        GL46.glTexParameteri(target, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_LINEAR);
        GL46.glTexParameteri(target, GL46.GL_TEXTURE_COMPARE_MODE, GL46.GL_NONE);
        GL46.glTexParameteri(target, GL46.GL_TEXTURE_WRAP_S, GL46.GL_CLAMP_TO_EDGE);
        GL46.glTexParameteri(target, GL46.GL_TEXTURE_WRAP_T, GL46.GL_CLAMP_TO_EDGE);
        
        this.unbind();
        
        return true;
    }

    @Override
    public boolean dispose() {
        GL46.glDeleteTextures(this.handle);
        return true;
    }
    
    @Override
    public boolean bind() {
        GL46.glBindTexture(DepthTexture.TARGET, this.handle);
        return true;
    }


    @Override
    public boolean unbind() {
        GL46.glBindTexture(DepthTexture.TARGET, 0);
        return true;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
