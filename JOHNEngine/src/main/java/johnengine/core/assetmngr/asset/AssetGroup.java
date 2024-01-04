package johnengine.core.assetmngr.asset;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.assetmngr.AssetManager;

public class AssetGroup implements IAsset {

    protected final AssetManager assetManager;
    protected final String name;
    protected final List<String> assets;
    
    public AssetGroup(String name, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.name = name;
        this.assets = new ArrayList<>();
    }
    
    @Override
    public void deload() {
        for( String assetName : this.assets )
        this.assetManager.deloadAsset(assetName);
    }
    
    public AssetGroup putAsset(String assetName) {
        if( assetName != null )
        this.assets.add(assetName);
        
        return this;
    }
    
    public AssetGroup putAndDeclare(IAsset asset) {
        if( asset != null )
        {
            this.assetManager.declareAsset(asset);
            this.putAsset(asset.getName());
        }
        
        return this;
    }
    
    public AssetGroup removeAsset(String assetName) {
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
