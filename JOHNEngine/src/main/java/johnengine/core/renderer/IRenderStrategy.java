package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderStrategy {

    public void prepare();
    
    public void newBuffer();
    
    public boolean executeStrategoid(IRenderable target);
    
    public void render();
    
    public IRenderer getRenderer();
    
    public boolean canRender();
    
    public void dispose();
}
