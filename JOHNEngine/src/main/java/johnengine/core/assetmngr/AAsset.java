package johnengine.core.assetmngr;

public abstract class AAsset implements IAsset {

    protected final String name;
    protected final String path;
    protected Object asset;
    
    protected AAsset(String name, String path) {
        this.name = name;
        this.path = path;
        this.asset = null;
    }
    
    
    public void load() {
        
    }
    
    public void nullify() {
        this.asset = null;
    }
    
    
    public String getName() {
        return this.name;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public Object getAsset() {
        return this.asset;
    }
}
