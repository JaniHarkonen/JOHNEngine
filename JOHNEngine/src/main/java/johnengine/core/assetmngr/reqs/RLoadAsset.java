package johnengine.core.assetmngr.reqs;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.reqmngr.IRequestContext;

public class RLoadAsset extends AAssetRequest {

    public RLoadAsset(AAsset asset) {
        super(asset);
    }

    @Override
    public void process(IRequestContext context) {
        this.asset.load();
    }
    
    @Override
    public boolean canQueue() {
        return this.asset.isDeloaded();
    }
}
