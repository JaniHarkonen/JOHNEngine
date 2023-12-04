package johnengine.core.assetmngr.asset;

public interface IDeloadProcessor<A extends IAsset> {

    public void deloadAsset(A asset);
    
    public void processAssetDeloads();
}
