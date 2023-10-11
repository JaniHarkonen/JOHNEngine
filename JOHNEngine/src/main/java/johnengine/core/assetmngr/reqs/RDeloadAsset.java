package johnengine.core.assetmngr.reqs;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.reqmngr.IRequestContext;

public class RDeloadAsset extends AAssetRequest {

    public RDeloadAsset(AAsset asset) {
        super(asset);
    }

    @Override
    public void process(IRequestContext context) {
        this.asset.deload();
    }
    
    @Override
    public boolean canQueue() {
        return this.asset.isLoaded();
    }
}
