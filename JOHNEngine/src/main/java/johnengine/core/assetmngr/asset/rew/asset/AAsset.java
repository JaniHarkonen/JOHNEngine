package johnengine.core.assetmngr.asset.rew.asset;

public abstract class AAsset<T, L extends AAssetLoader> implements IAsset {

    protected final String name;
    protected final boolean isPersistent;
    protected T asset;
    
    protected AAsset(String name, boolean isPersistent, T preloadedAsset) {
        this.name = name;
        this.isPersistent = isPersistent;
        this.asset = preloadedAsset;
    }
    
    protected AAsset(String name) {
        this(name, false, null);
    }
    
    
    public T getDefault() {
        return null;
    }
    
    public T get() {
        if( this.asset == null )
        return this.getDefault();
        
        return this.asset;
    }
    
    public void deload() {
        if( this.isPersistent )
        return;
        
        this.deloadImpl();
        this.asset = null;
    }
    
    protected abstract void deloadImpl();
    
    
    public T getAssetUnchecked() {
        return this.asset;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public boolean isPersistent() {
        return this.isPersistent;
    }
}
