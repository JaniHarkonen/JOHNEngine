package johnengine.core.renderer.rewrite;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.core.assetmngr.asset.IAsset;

public interface IRenderAsset extends IAsset {
    public IGraphicsAsset<?> getGraphics();
}
