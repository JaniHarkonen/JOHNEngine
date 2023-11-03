package johnengine.core.assetmngr.asset.rew.asset;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.assetmngr.asset.rew.AssetManager;

public class AssetGroup2 implements IAsset {

    protected final AssetManager assetManager;
    protected final String name;
    protected final List<String> assets;
    
    public AssetGroup2(String name, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.name = name;
        this.assets = new ArrayList<>();
    }
    
    
    @Override
    public void deload() {        
        for( String assetName : this.assets )
        this.assetManager.deloadAsset(assetName);
    }
    
    public AssetGroup2 putAsset(String assetName) {
        if( assetName != null )
        this.assets.add(assetName);
        
        return this;
    }
    
    public AssetGroup2 putAndDeclare(IAsset asset) {
        if( asset != null )
        {
            this.assetManager.declareAsset(asset);
            this.putAsset(asset.getName());
        }
        
        return this;
    }
    
    public AssetGroup2 removeAsset(String assetName) {
        if( assetName != null )
        {
            for( int i = 0; i < this.assets.size(); i++ )
            {
                if( this.assets.get(i) == assetName )
                {
                    this.assets.remove(i);
                    break;
                }
            }
        }
        
        return this;
    }
    
    
    /************************** GETTERS **************************/
    
    @Override
    public String getName() {
        return this.name;
    }
}
