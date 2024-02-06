package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderContext {

    public void startRenderBuffer();
    
    public IRenderable nextInstance();
}
