package johnengine.basic.renderer.asset;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRenderAsset;

public abstract class ARendererAsset<G extends IGraphicsAsset<?>, D> implements IRenderAsset {

    G graphics;
    
    protected D data;
    protected String name;
    protected boolean isPersistent;
    
    protected ARendererAsset(String name, boolean isPersistent) {
        this.graphics = null;
        this.name = name;
        this.isPersistent = isPersistent;
        this.data = null;
    }
    
    
    @Override
    public void deload() {
        if( this.isPersistent )
        return;
        
        if( this.graphics != null )
        this.graphics.dispose();
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
    
    public String getName() {
        return this.name;
    }
    
    D getDataDirect() {
        return this.data;
    }
}
