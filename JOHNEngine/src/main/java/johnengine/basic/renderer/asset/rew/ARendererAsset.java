package johnengine.basic.renderer.asset.rew;

import johnengine.core.assetmngr.asset.rew.asset.AAsset;
import johnengine.core.renderer.IDrawable;

public abstract class ARendererAsset<T> extends AAsset<T> implements IDrawable {

    protected ARendererAsset(String name, boolean isPersistent) {
        super(name, isPersistent, null);
    }
}
