package johnengine.basic.renderer.asset;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.renderer.IDrawable;

public abstract class ARendererAsset<T> extends AAsset<T> implements IDrawable {

    protected ARendererAsset(String name, boolean isPersistent) {
        super(name, isPersistent, null);
    }
}
