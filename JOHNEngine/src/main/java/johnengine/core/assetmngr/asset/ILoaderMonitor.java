package johnengine.core.assetmngr.asset;

public interface ILoaderMonitor<A extends IAsset> {

    public void assetLoaded(A asset);
    public void processLoadedAssets();
}
