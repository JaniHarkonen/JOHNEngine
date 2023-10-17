package johnengine.core.assetmngr.asset;

public abstract class AAsset implements IAsset {
    
    /**
     * The status of the asset indicating with regards to (de)loading.
     * 
     * @author User
     *
     */
    public enum STATUS {
        /**
         * This asset has been queued by the AssetManager for (de)loading.
         */
        QUEUED,
        /**
         * The asset has been deloaded (or it hasn't been loaded yet).
         */
        DELOADED,
        
        /**
         * The asset is being deloaded (deload()-method has been called).
         */
        DELOADING,
        
        /**
         * The asset is being loaded (load()-method has been called).
         */
        LOADING,
        
        /**
         * The asset has been loaded and contains a reference to the 
         * object that was loaded.
         */
        LOADED
    }

    protected final String name;
    protected final String path;
    protected Object asset;
    protected STATUS loadingStatus;
    
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
    
    protected abstract void loadImpl();
    
    protected abstract void deloadImpl();
    
    protected Object getDefault() {
        return null;
    }
    
    
    /************************* GETTERS ************************/
    
    public String getName() {
        return this.name;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public Object get() {
        if( this.asset != null )
        return this.asset;
        
        return this.getDefault();
    }
    
    public boolean isDeloaded() {
        return (this.loadingStatus == STATUS.DELOADED);
    }
    
    public boolean isDeloading() {
        return (this.loadingStatus == STATUS.DELOADING); 
    }
    
    public boolean isLoading() {
        return (this.loadingStatus == STATUS.LOADING);
    }
    
    public boolean isLoaded() {
        return (this.loadingStatus == STATUS.LOADED);
    }
    
    public boolean isQueued() {
        return (this.loadingStatus == STATUS.QUEUED);
    }
    
    public STATUS getStatus() {
        return this.loadingStatus;
    }
    
    
    /************************* SETTERS ************************/
    
    public void deloaded() {
        this.loadingStatus = STATUS.DELOADED;
    }
    
    public void deloading() {
        this.loadingStatus = STATUS.DELOADING;
    }
    
    public void loading() {
        this.loadingStatus = STATUS.LOADING;
    }
    
    public void loaded() {
        this.loadingStatus = STATUS.LOADED;
    }
    
    public void queued() {
        this.loadingStatus = STATUS.QUEUED;
    }
}
