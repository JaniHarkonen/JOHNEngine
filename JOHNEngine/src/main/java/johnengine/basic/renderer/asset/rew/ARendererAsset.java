package johnengine.basic.renderer.asset.rew;

import johnengine.core.assetmngr.asset.rew.asset.IAsset;

public abstract class ARendererAsset<T> implements IAsset {

    protected T asset;
    
    
    public void setAsset(T asset) {
        this.asset = asset;
    }
}
