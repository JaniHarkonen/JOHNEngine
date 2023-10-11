package johnengine.core.assetmngr.reqs;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.reqmngr.ARequest;

public abstract class AAssetRequest extends ARequest {

    protected AAsset asset;
    
    protected AAssetRequest(AAsset asset) {
        this.asset = asset;
    }
    
    public void queueAsset() {
        asset.queued();
    }
    
    public abstract boolean canQueue();
}
