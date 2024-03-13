package johnengine.basic.assets;

import johnengine.core.assetmngr.asset.IAsset;

public interface IGraphicsAsset extends IAsset {

    public void setGraphicsStrategy(IGraphicsStrategy graphicsStrategy);
    
    public IGraphicsStrategy getGraphicsStrategy();
    
}
