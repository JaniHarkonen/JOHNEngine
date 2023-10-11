package johnengine.core.assetmngr.reqs;

import johnengine.core.assetmngr.AAsset;
import johnengine.core.reqmngr.ARequest;

public abstract class AAssetRequest extends ARequest {

    protected AAsset asset;
    
    protected AAssetRequest(AAsset asset) {
        this.asset = asset;
    }
}
