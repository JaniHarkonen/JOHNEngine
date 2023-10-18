package johnengine.core.assetmngr.asset;

public abstract class AAsset<T> implements IAsset {
    
    /**
     * This asset has been queued by the AssetManager for (de)loading.
     */
    public static final int STATUS_QUEUED = 1;
    
    /**
     * The asset has been deloaded (or it hasn't been loaded yet).
     */
    public static final int STATUS_DELOADED = 2;
    
    /**
     * The asset is being deloaded (deload()-method has been called).
     */
    public static final int STATUS_DELOADING = 3;
    
    /**
     * The asset is being loaded (load()-method has been called).
     */
    public static final int STATUS_LOADING = 4;
    
    /**
     * The asset has been loaded and contains a reference to the 
     * object that was loaded.
     */
    public static final int STATUS_LOADED = 5;
    

    protected final String name;
    protected final String path;
    protected T asset;
    protected int loadingStatus;
    
    protected AAsset(String name, String path) {
        this.name = name;
        this.path = path;
        this.asset = null;
        
        this.deloaded();
    }
    
    
    @Override
    public void load() {
        if( this.isLoaded() )
        return;
        
        this.loading();
        this.loadImpl();
        this.loaded();
    }
    
    @Override
    public void deload() {
        if( this.isDeloaded() )
        return;
        
        this.deloading();
        this.deloadImpl();
        this.asset = null;
        this.deloaded();
    }
    
    protected T getDefault() {
        return null;
    }
    
    protected abstract void loadImpl();
    
    protected abstract void deloadImpl();
    
    
    /************************* GETTERS ************************/
    
    public String getName() {
        return this.name;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public T get() {
        if( this.asset != null )
        return this.asset;
        
        return this.getDefault();
    }
    
    public boolean isDeloaded() {
        return (this.loadingStatus == STATUS_DELOADED);
    }
    
    public boolean isDeloading() {
        return (this.loadingStatus == STATUS_DELOADING); 
    }
    
    public boolean isLoading() {
        return (this.loadingStatus == STATUS_LOADING);
    }
    
    public boolean isLoaded() {
        return (this.loadingStatus == STATUS_LOADED);
    }
    
    public boolean isQueued() {
        return (this.loadingStatus == STATUS_QUEUED);
    }
    
    
    /************************* SETTERS ************************/
    
    public void deloaded() {
        this.loadingStatus = STATUS_DELOADED;
    }
    
    public void deloading() {
        this.loadingStatus = STATUS_DELOADING;
    }
    
    public void loading() {
        this.loadingStatus = STATUS_LOADING;
    }
    
    public void loaded() {
        this.loadingStatus = STATUS_LOADED;
    }
    
    public void queued() {
        this.loadingStatus = STATUS_QUEUED;
    }
}
