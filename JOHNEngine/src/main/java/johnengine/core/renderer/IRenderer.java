package johnengine.core.renderer;

import johnengine.core.winframe.IWindow;

public interface IRenderer {

    public void generateDefaults();
    
    public void initialize();
    
    public void generateRenderBuffer();
    
    public void render();
    
    public IWindow getWindow();
}
