package johnengine.basic.assets;

import johnengine.core.assetmngr.asset.IAsset;

public interface IRenderAsset extends IAsset {
    public IGraphicsAsset<?> getGraphics();
}
