package johnengine.core.renderer;

import johnengine.core.renderer.unimngr.UniformManager;
import johnengine.core.winframe.AWindowFramework;

public abstract class ARenderer {
    
    protected final UniformManager uniformManager;
    protected AWindowFramework hostWindow;
    
    protected ARenderer(AWindowFramework hostWindow, UniformManager uniformManager) {
        this.hostWindow = hostWindow;
        this.uniformManager = uniformManager;
    }

    
    public abstract void initialize();
    
    public abstract void render();
    
    
    public UniformManager getUniformManager() {
        return this.uniformManager;
    }
    
    public AWindowFramework getWindow() {
        return this.hostWindow;
    }
}
