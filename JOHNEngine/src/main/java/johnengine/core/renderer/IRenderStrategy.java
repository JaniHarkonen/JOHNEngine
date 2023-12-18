package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderStrategy {

    public void prepare();
    
    public void newBuffer();
    
    public boolean executeStrategoid(IRenderable target);
    
    public void preRender();
    
    public void render();
    
    public void postRender();
    
    public IRenderer getRenderer();
    
    public void dispose();
}
