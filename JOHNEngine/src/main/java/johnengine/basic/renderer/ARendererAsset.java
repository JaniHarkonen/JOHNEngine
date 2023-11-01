package johnengine.basic.renderer;

import johnengine.core.assetmngr.asset.AAsset;

public abstract class ARendererAsset<T> extends AAsset<T> {

    protected ARendererAsset(String name) {
        super(name, null, false, null);
    }
    
    
    public void setAsset(T asset) {
        this.asset = asset;
    }
}
