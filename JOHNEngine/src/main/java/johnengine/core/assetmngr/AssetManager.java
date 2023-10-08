package johnengine.core.assetmngr;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IEngineComponent;
import johnengine.core.reqmngr.RequestManager;
import johnengine.core.threadable.AThreadable;

public final class AssetManager extends AThreadable implements IEngineComponent {

    private final Map<String, AAsset> assetMap;
    private final RequestManager requestManager;
    
    
    public static AssetManager setup() {
        return new AssetManager();
    }
    
    public AssetManager() {
        this.assetMap = new HashMap<String, AAsset>();
        this.requestManager = new RequestManager();
    }
    
    
    public void declareAsset(AAsset asset) {
        this.assetMap.put(asset.getName(), asset);
    }
    
    public AssetGroup createAssetGroup(String groupName) {
        return new AssetGroup(groupName, this);
    }
    
    public void loadAsset(String assetName) {
        
    }
    
    public void deloadAsset(String assetName) {
        
    }

    @Override
    public void beforeTick(float deltaTime) {
        
    }

    @Override
    public void afterTick(float deltaTime) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }
}
