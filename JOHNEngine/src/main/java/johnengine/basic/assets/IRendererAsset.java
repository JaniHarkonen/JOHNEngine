package johnengine.basic.assets;

import johnengine.core.assetmngr.asset.IAsset;

public interface IRendererAsset extends IAsset {
    public IGraphicsAsset<?> getGraphics();
}
