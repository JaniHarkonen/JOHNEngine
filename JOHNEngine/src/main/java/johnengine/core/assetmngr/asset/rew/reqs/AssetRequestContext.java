package johnengine.core.assetmngr.asset.rew.reqs;

import johnengine.core.assetmngr.asset.rew.AssetManager;
import johnengine.core.reqmngr.IRequestContext;

public class AssetRequestContext implements IRequestContext {

    public AssetManager assetManager;
    
    public AssetRequestContext(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
