package johnengine.basic.opengl.renderer.shadows;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;
import johnengine.core.logger.Logger;

public class ShadowMapBuffer implements IGeneratable, IBindable {

    private int fboDepthMapHandle;
    private DepthTexture[] depthTextures;
    
    public ShadowMapBuffer(DepthTexture[] depthTextures) {
        this.fboDepthMapHandle = 0;
        this.depthTextures = depthTextures;
    }
    
    
    @Override
    public boolean generate() {
        this.fboDepthMapHandle = GL46.glGenFramebuffers();
        
            // Generate depth textures
        for( DepthTexture texture : this.depthTextures )
        texture.generate();
        
        this.bind();
        GL46.glFramebufferTexture2D(GL46.GL_FRAMEBUFFER, GL46.GL_DEPTH_ATTACHMENT, GL46.GL_TEXTURE_2D, this.depthTextures[0].getHandle(), 0);
        
        GL46.glDrawBuffer(GL46.GL_NONE);
        GL46.glReadBuffer(GL46.GL_NONE);
        
        if( GL46.glCheckFramebufferStatus(GL46.GL_FRAMEBUFFER) != GL46.GL_FRAMEBUFFER_COMPLETE )
        {
            Logger.log(
                Logger.VERBOSITY_MINIMAL, 
                Logger.SEVERITY_WARNING, 
                this, 
                "Could not create frame buffer for cascaded shadow maps!"
            );
        }
        
        this.unbind();
        
        return true;
    }
    
    @Override
    public boolean dispose() {
        GL46.glDeleteFramebuffers(this.fboDepthMapHandle);
        return true;
    }
    
    @Override
    public boolean bind() {
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, this.fboDepthMapHandle);
        return true;
    }
    
    @Override
    public boolean unbind() {
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, 0);
        return true;
    }
    
    
    public int getDepthMapFBOHandle() {
        return this.fboDepthMapHandle;
    }
    
    public DepthTexture[] getDepthTextures() {
        return this.depthTextures;
    }
    
    public DepthTexture getDepthTexture(int depthLevel) {
        return this.depthTextures[depthLevel];
    }
}
