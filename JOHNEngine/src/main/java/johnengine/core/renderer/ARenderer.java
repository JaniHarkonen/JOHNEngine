package johnengine.core.renderer;

import johnengine.core.winframe.AWindowFramework;

public abstract class ARenderer {
    
    protected AWindowFramework hostWindow;
    
    protected ARenderer(AWindowFramework hostWindow) {
        this.hostWindow = hostWindow;
    }

    public abstract void initialize();
    
    public abstract void render();
}
