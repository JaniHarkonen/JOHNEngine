package johnengine.basic.opengl.renderer;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderStrategy;

public class DefaultRenderBufferStrategy implements IRenderBufferStrategy {
    
    @Override
    public void execute(IRenderContext renderContext, IRenderStrategy renderStrategy) {
        //InstanceManager<AWorldObject> worldManager = world.getInstances();
        //worldManager.resetIterator();
        renderContext.startRenderBuffer();
        
        /*AWorldObject instance;
        while( (instance = worldManager.nextActiveInstance()) != null )
        instance.render(renderStrategy);*/
        IRenderable instance;
        while( (instance = renderContext.nextInstance()) != null )
        instance.render(renderStrategy);
        
        renderStrategy.newBuffer();
    }
}
