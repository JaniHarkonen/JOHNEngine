package johnengine.core.assetmngr;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.assetmngr.reqs.AssetRequestContext;
import johnengine.core.assetmngr.reqs.RDeloadAsset;
import johnengine.core.assetmngr.reqs.RLoadAsset;
import johnengine.core.threadable.AThreadable;

public final class AssetManager extends AThreadable implements IEngineComponent {
    
    /**
     * Default number of worker threads available for the AssetManager. 
     */
    public static final int NUMBER_OF_THREADS = 4;

    private final Map<String, AAsset<?>> assetMap;
    private final AssetRequestManager requestManager;
    
    public static AssetManager setup() {
        AssetManager assetManager = new AssetManager();
        assetManager.start();
        return assetManager;
    }
    
    public AssetManager() {
        this.assetMap = new HashMap<>();
        this.requestManager = new AssetRequestManager(
            NUMBER_OF_THREADS, 
            new AssetRequestContext(this)
        );
    }
    
        // Declares an asset and places a reference to it in the asset map
    public AssetManager declareAsset(AAsset<?> asset) {
        this.assetMap.put(asset.getName(), asset);
        return this;
    }
    
        // Creates an asset group and associates this asset manager with it
    public AssetGroup createAssetGroup(String groupName) {
        return new AssetGroup(groupName, this);
    }
    
        // Creates and schedules an asset loading request
    public AssetManager loadAsset(String assetName) {
        AAsset<?> asset = this.assetMap.get(assetName);
        this.requestManager.request(new RLoadAsset(asset));
        return this;
    }
    
        // Creates and schedules an asset de-loading request
    public AssetManager deloadAsset(String assetName) {
        AAsset<?> asset = this.assetMap.get(assetName);
        this.requestManager.request(new RDeloadAsset(asset));
        return this;
    }
    
        // Returns a reference to an asset from the asset map given its name
        // or NULL, if no such asset has been declared
    public AAsset<?> getAsset(String assetName) {
        return this.assetMap.get(assetName);
    }

    @Override
    public void beforeTick(float deltaTime) {
        
    }

    @Override
    public void afterTick(float deltaTime) {
        this.requestManager.newBuffer();
    }

    @Override
    public void start() {
        this.startProcess();
    }

    @Override
    public void loop() {
        while( true )
        this.requestManager.processRequests();
    }

    @Override
    public void stop() {
        for( Map.Entry<String, AAsset<?>> en : this.assetMap.entrySet() )
        {
            String key = en.getKey();
            this.assetMap.get(key).deload();
            this.assetMap.remove(key);
        }
    }
}
