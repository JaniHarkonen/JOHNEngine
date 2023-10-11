package johnengine.core.assetmngr.reqs;

import johnengine.core.assetmngr.AAsset;
import johnengine.core.reqmngr.IRequestContext;

public class RDeloadAsset extends AAssetRequest {

    public RDeloadAsset(AAsset asset) {
        super(asset);
    }

    @Override
    public void process(IRequestContext context) {
        this.asset.deload();
    }
}
