package johnengine.core.renderer;

import johnengine.core.window.IWindow;

public interface IRenderer {

    public void generateDefaults();
    
    public void initialize();
    
    public void generateRenderBuffers();
    
    public void render();
    
    public IWindow getWindow();
    
    public RenderPassManager getRenderPassManager();
}
