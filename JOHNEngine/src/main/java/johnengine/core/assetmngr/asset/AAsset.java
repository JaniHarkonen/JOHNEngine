package johnengine.core.assetmngr.asset;

public abstract class AAsset<T> implements IAsset {

    protected final String name;
    protected final boolean isPersistent;
    protected T asset;
    protected T actualAsset;
    
    public AAsset(String name, boolean isPersistent, T preloadedAsset) {
        this.name = name;
        this.isPersistent = isPersistent;
        this.asset = preloadedAsset;
        this.actualAsset = this.asset;
    }
    
    public AAsset(String name) {
        this(name, false, null);
        this.actualAsset = null;
    }
    
    
    public abstract T getDefault();
    
    public T get() {
        /*if( this.asset == null )
        return this.getDefault();
        
        return this.asset;*/
        return this.asset;
    }
    
    public T getUnsafe() {
        return this.actualAsset;
    }
    
    public void deload() {
        if( this.isPersistent )
        return;
        
        this.deloadImpl();
        this.deloaded();
    }
    
    protected abstract void deloadImpl();
    
    
    public void setAsset(T asset) {
        this.actualAsset = asset;
        this.asset = this.actualAsset;
    }
    
    public void deloaded() {
        this.actualAsset = null;
        this.asset = this.getDefault();
    }
    
    /*public T getAssetUnchecked() {
        return this.asset;
    }*/
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public boolean isPersistent() {
        return this.isPersistent;
    }
    
    public boolean hasLoaded() {
        return (this.actualAsset != null);
    }
}
