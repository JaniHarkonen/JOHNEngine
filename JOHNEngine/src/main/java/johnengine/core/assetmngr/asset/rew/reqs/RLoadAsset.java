package johnengine.core.assetmngr.asset.rew.reqs;

import johnengine.core.assetmngr.asset.rew.asset.AAssetLoader;
import johnengine.core.reqmngr.IRequestContext;

public class RLoadAsset extends AAssetRequest {

    public RLoadAsset(AAssetLoader loader) {
        super(loader);
    }

    
    @Override
    public void process(IRequestContext context) {
        this.loader.load();
    }
    
    @Override
    public boolean canQueue() {
        return this.loader.isInactive();
    }
}
