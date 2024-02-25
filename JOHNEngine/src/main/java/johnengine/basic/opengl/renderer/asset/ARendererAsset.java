package johnengine.basic.opengl.renderer.asset;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.core.assetmngr.asset.IDeloadProcessor;

public abstract class ARendererAsset<G extends IGraphicsAsset<?>, D> implements IRendererAsset {

    G graphics;
    
    protected IDeloadProcessor<IRendererAsset> deloader;
    protected D data;
    protected String name;
    protected boolean isPersistent;
    
    protected ARendererAsset(String name, boolean isPersistent) {
        this.graphics = null;
        this.deloader = null;
        this.name = name;
        this.isPersistent = isPersistent;
        this.data = null;
    }
    
    
    @Override
    public void deload() {
        if( this.isPersistent )
        return;
        
        if( this.deloader != null )
        this.deloader.deloadAsset(this);
    }
    
    
    public abstract D getDefault();
    public abstract G getDefaultGraphics();
    
    public D getData() {
        if( this.data == null )
        return this.getDefault();
        
        return this.data;
    }
    
    @Override
    public G getGraphics() {
        if( this.graphics == null )
        return this.getDefaultGraphics();
        
        return this.graphics;
    }
    
    
    public void setDeloader(IDeloadProcessor<IRendererAsset> deloader) {
        this.deloader = deloader;
    }
    
    
    public String getName() {
        return this.name;
    }
    
    D getDataDirect() {
        return this.data;
    }
}
