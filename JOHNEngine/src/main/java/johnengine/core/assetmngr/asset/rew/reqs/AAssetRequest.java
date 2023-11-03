package johnengine.core.assetmngr.asset.rew.reqs;

import johnengine.core.assetmngr.asset.rew.asset.AAssetLoader;
import johnengine.core.reqmngr.ARequest;

public abstract class AAssetRequest extends ARequest {

    protected AAssetLoader loader;
    
    protected AAssetRequest(AAssetLoader loader) {
        this.loader = loader;
    }
    
    
    public void queueAsset() {
        loader.queued();
    }
    
    public abstract boolean canQueue();
}
