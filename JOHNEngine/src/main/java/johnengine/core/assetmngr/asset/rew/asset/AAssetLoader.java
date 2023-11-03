package johnengine.core.assetmngr.asset.rew.asset;

public abstract class AAssetLoader {
    public static final int STATE_INACTIVE = 1;
    public static final int STATE_QUEUED = 2;
    public static final int STATE_LOADING = 3;
    public static final int STATE_LOADED = 4;
    
    protected int status;
    protected String path;
    
    protected AAssetLoader(String path) {
        this.path = path;
        this.reset();
    }
    
    protected AAssetLoader() {
        this(null);
    }
    
    
    public void load() {
        this.loading();
        this.loadImpl();
        this.loaded();
    }
    
    protected abstract void loadImpl();
    
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public void reset() {
        this.status = STATE_INACTIVE;
    }
    
    public void queued() {
        this.status = STATE_QUEUED;
    }
    
    public void loading() {
        this.status = STATE_LOADING;
    }
    
    public void loaded() {
        this.status = STATE_LOADED;
    }
    
    
    public boolean isInactive() {
        return (this.status == STATE_INACTIVE);
    }
    
    public boolean isQueued() {
        return (this.status == STATE_QUEUED);
    }
    
    public boolean isLoading() {
        return (this.status == STATE_LOADING);
    }
    
    public boolean isLoaded() {
        return (this.status == STATE_LOADED);
    }
    
    public String getPath() {
        return this.path;
    }
}