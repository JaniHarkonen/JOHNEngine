package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderPass {

    public void prepare();
    
    public void newBuffer();
    
    public boolean executeStrategoid(IRenderable target);
    
    public void render();
    
    public void setRenderContext(IRenderContext renderContext);
    
    public IRenderer getRenderer();
    
    public IRenderContext getRenderContext();
    
    public boolean canRender();
    
    public void dispose();
}
