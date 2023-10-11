package johnengine.core.assetmngr.reqs;

import johnengine.core.assetmngr.AssetManager;
import johnengine.core.reqmngr.IRequestContext;

public class AssetRequestContext implements IRequestContext {

    public AssetManager assetManager;
    
    public AssetRequestContext(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
