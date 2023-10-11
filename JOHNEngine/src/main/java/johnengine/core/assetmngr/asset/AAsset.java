package johnengine.core.assetmngr.asset;

public abstract class AAsset implements IAsset {

    protected final String name;
    protected final String path;
    protected Object asset;
    protected boolean isLoaded;
    
    protected AAsset(String name, String path) {
        this.name = name;
        this.path = path;
        this.asset = null;
        this.isLoaded = false;
    }
    
    
    @Override
    public void load() {
        if( this.isLoaded )
        return;
        
        this.loadImpl();
        this.isLoaded = true;
    }
    
    @Override
    public void deload() {
        this.deloadImpl();
        this.asset = null;
        this.isLoaded = false;
    }
    
    protected abstract void loadImpl();
    
    protected abstract void deloadImpl();
    
    
    public String getName() {
        return this.name;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public Object getAsset() {
        return this.asset;
    }
    
    public boolean isLoaded() {
        return this.isLoaded;
    }
}
