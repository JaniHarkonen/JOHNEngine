package johnengine.basic.renderer.asset;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.renderer.ARenderer;

public abstract class ARendererAsset<T extends ARendererAsset.Data> extends AAsset<T> {
    
    public static class Data {
        public void generate() {}
        public void dispose() {}
    }
    
    
    protected ARenderer renderer;

    protected ARendererAsset(String name, boolean isPersistent, T preloadedAsset) {
        super(name, isPersistent, preloadedAsset);
    }
    
    protected ARendererAsset(String name, boolean isPersistent, ARenderer renderer) {
        super(name, isPersistent, null);
        this.renderer = renderer;
    }
    
    
    public abstract void generate();
    public abstract void bind();
    public abstract void unbind();
    
    public void loadingFinished() {
        this.renderer.generateAsset(this.get());
    }
    
    
    public void setRenderer(ARenderer renderer) {
        this.renderer = renderer;
    }
}
