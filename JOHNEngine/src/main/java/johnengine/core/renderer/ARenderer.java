package johnengine.core.renderer;

import johnengine.basic.renderer.rew.ARenderRequest;
import johnengine.core.renderer.unimngr.UniformManager;
import johnengine.core.reqmngr.BufferedRequestManager;
import johnengine.core.winframe.AWindowFramework;

public abstract class ARenderer {
    
    protected final UniformManager uniformManager;
    protected final BufferedRequestManager renderRequestManager;
    protected AWindowFramework hostWindow;
    
    protected ARenderer(AWindowFramework hostWindow, UniformManager uniformManager) {
        this.hostWindow = hostWindow;
        this.uniformManager = uniformManager;
        this.renderRequestManager = new BufferedRequestManager();
    }

    
    public abstract void initialize();
    
    public abstract void render();
    
    public abstract void newBuffer();
    
    public void bufferRenderRequest(ARenderRequest request) {
        this.renderRequestManager.request(request);
    }
    
    
    public UniformManager getUniformManager() {
        return this.uniformManager;
    }
    
    public AWindowFramework getWindow() {
        return this.hostWindow;
    }
}
