package johnengine.core.assetmngr;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.reqs.AssetRequestContext;
import johnengine.core.assetmngr.reqs.RDeloadAsset;
import johnengine.core.assetmngr.reqs.RLoadAsset;
import johnengine.core.threadable.AThreadable;
import johnengine.testing.DebugUtils;

public final class AssetManager extends AThreadable implements IEngineComponent {
    
    public static final int NUMBER_OF_THREADS = 4;

    private final Map<String, AAsset> assetMap;
    private final AssetRequestManager requestManager;
    
    public static AssetManager setup() {
        AssetManager assetManager = new AssetManager();
        assetManager.start();
        return assetManager;
    }
    
    public AssetManager() {
        this.assetMap = new HashMap<String, AAsset>();
        this.requestManager = new AssetRequestManager(NUMBER_OF_THREADS, new AssetRequestContext(this));
    }
    
    
    public AssetManager declareAsset(AAsset asset) {
        this.assetMap.put(asset.getName(), asset);
        return this;
    }
    
    public AssetGroup createAssetGroup(String groupName) {
        return new AssetGroup(groupName, this);
    }
    
    public AssetManager loadAsset(String assetName) {
        AAsset asset = this.assetMap.get(assetName);
        this.requestManager.request(new RLoadAsset(asset));
        return this;
    }
    
    public AssetManager loadGroup(AssetGroup assetGroup) {
        assetGroup.load();
        return this;
    }
    
    public AssetManager deloadAsset(String assetName) {
        AAsset asset = this.assetMap.get(assetName);
        this.requestManager.request(new RDeloadAsset(asset));
        return this;
    }
    
    public AssetManager deloadGroup(AssetGroup assetGroup) {
        assetGroup.deload();
        return this;
    }
    
    public AAsset getAsset(String assetName) {
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
        for( Map.Entry<String, AAsset> en : this.assetMap.entrySet() )
        {
            String key = en.getKey();
            this.assetMap.get(key).deload();
            this.assetMap.remove(key);
        }
    }
}
