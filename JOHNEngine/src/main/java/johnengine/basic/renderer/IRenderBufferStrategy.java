package johnengine.basic.renderer;

import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderStrategy;

public interface IRenderBufferStrategy {

    //public void execute(JWorld world, IRenderStrategy renderStrategy);
    public void execute(IRenderContext renderContext, IRenderStrategy renderStrategy);  
}
