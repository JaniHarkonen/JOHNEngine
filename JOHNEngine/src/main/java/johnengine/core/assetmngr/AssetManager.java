package johnengine.core.assetmngr;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.asset.AAssetLoader;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.assetmngr.asset.IAsset;
import johnengine.core.assetmngr.reqs.AssetRequestContext;
import johnengine.core.assetmngr.reqs.AssetRequestManager;
import johnengine.core.assetmngr.reqs.RLoadAsset;
import johnengine.core.threadable.AThreadable;

public final class AssetManager extends AThreadable implements IEngineComponent {
    
    /**
     * Default number of worker threads available for the AssetManager. 
     */
    public static final int NUMBER_OF_THREADS = 4;

    private final Map<String, IAsset> assetMap;
    private final AssetRequestManager requestManager;
    
    private volatile boolean isRunning;
    
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
        this.isRunning = false;
    }
    
    
        // Declares an asset and places a reference to it in the asset map
    public AssetManager declareAsset(IAsset asset) {
        String assetName = asset.getName();
        
        if( this.assetMap.get(assetName) == null )
        this.assetMap.put(assetName, asset);
        
        return this;
    }
    
    public AssetManager delistAsset(String assetName) {
        this.assetMap.remove(assetName);
        return this;
    }
    
        // Creates an asset group and associates this asset manager with it
    public AssetGroup createAssetGroup(String groupName) {
        return new AssetGroup(groupName, this);
    }
    
        // Creates and schedules an asset loading request
    /*public AssetManager loadAsset(String assetName) {
        AAsset<?, ?> asset = (AAsset<?, ?>) this.assetMap.get(assetName);
        
        if( asset != null )
        this.requestManager.request(new RLoadAsset(asset.createLoader()));
        
        return this;
    }*/
    
    public AssetManager loadFrom(String path, AAssetLoader loader) {
        if( loader != null )
        {
            loader.setPath(path);
            this.requestManager.request(new RLoadAsset(loader));
        }
        
        return this;
    }

    public AssetManager load(AAssetLoader loader) {
        if( loader != null )
        this.requestManager.request(new RLoadAsset(loader));
        
        return this;
    }
    
        // Creates and schedules an asset de-loading request
    public AssetManager deloadAsset(String assetName) {
        IAsset asset = (IAsset) this.assetMap.get(assetName);
        
        if( asset != null )
        asset.deload();
        
        return this;
    }
    
    public AssetManager deloadAndDelist(String assetName) {
        this.deloadAsset(assetName);
        this.delistAsset(assetName);
        
        return this;
    }
    
        // Returns a reference to an asset from the asset map given its name
        // or NULL, if no such asset has been declared
    public IAsset getAsset(String assetName) {
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
        this.isRunning = true;
        super.start();
    }

    @Override
    public void loop() {
        while( this.isRunning )
        this.requestManager.processRequests();
    }

    @Override
    public void stop() {
        this.isRunning = false;
        
        for( Map.Entry<String, IAsset> en : this.assetMap.entrySet() )
        {
            String key = en.getKey();
            this.assetMap.get(key).deload();
        }
        
        this.assetMap.clear();
    }
}
