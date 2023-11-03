package johnengine.core.assetmngr.asset;

//public abstract class AAsset<T> implements IAsset {
    
    /**
     * This asset has been queued by the AssetManager for (de)loading.
     */
    //public static final int STATUS_QUEUED = 1;
    
    /**
     * The asset has been deloaded (or it hasn't been loaded yet).
     */
    //public static final int STATUS_DELOADED = 2;
    
    /**
     * The asset is being deloaded (deload()-method has been called).
     */
    //public static final int STATUS_DELOADING = 3;
    
    /**
     * The asset is being loaded (load()-method has been called).
     */
    //public static final int STATUS_LOADING = 4;
    
    /**
     * The asset has been loaded and contains a reference to the 
     * object that was loaded.
     */
    /*public static final int STATUS_LOADED = 5;
    
    protected final boolean isPersistent;
    protected final String name;
    protected final String relativePath;
    protected T asset;
    protected int loadingStatus;
    
    protected AAsset(String name, String relativePath, boolean isPersistent, T preloadedAsset) {
        this.isPersistent = isPersistent;
        this.name = name;
        this.relativePath = relativePath;
        this.asset = preloadedAsset;
        
        if( preloadedAsset == null )
        this.deloaded();
        else
        this.loaded();
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
        if( this.isDeloaded() || this.isPersistent )
        return;
        
        this.deloading();
        this.deloadImpl();
        this.deloaded();
        this.asset = null;
    }
    
    protected T getDefault() {
        return null;
    }
    */
    /**
     * Each asset should have its own loading implementation 
     * (loadImpl()-method) which will be called when load() is 
     * called. This method should read the asset from an external 
     * source and store a representation of it in the 
     * <i>AAsset.asset</i> field.
     */
    //protected abstract void loadImpl();
    
    /**
     * Each asset should have its own deloading implementation 
     * (deloadImpl()-method) which will be called when deload() is 
     * called. This method should free up the memory consumed by
     * the asset (stored in the <i>AAsset.asset</i> field. The
     * field does not need to be reset as deload() already does
     * this in order to inform the garbage collector.
     */
    //protected abstract void deloadImpl();
    
    
    /************************* GETTERS ************************/
    
    /*public String getName() {
        return this.name;
    }
    
    public String getPath() {
        return this.relativePath;
    }
    
    public T get() {
        if( this.isLoaded() )
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
    }*/
    
    
    /************************* SETTERS ************************/
    
    /*public void deloaded() {
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
}*/
