package johnengine.core.renderer;

import johnengine.core.winframe.rewrite.AWindowFramework;

public interface IRenderer {

    public void generateDefaults();
    
    public void initialize();
    
    public void generateRenderBuffer();
    
    public void render();
    
    public AWindowFramework getWindow();
}
