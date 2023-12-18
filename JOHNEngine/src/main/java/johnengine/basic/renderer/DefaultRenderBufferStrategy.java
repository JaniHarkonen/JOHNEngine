package johnengine.basic.renderer;

import johnengine.basic.InstanceManager;
import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.core.renderer.IRenderStrategy;

public class DefaultRenderBufferStrategy implements IRenderBufferStrategy {
    
    @Override
    public void execute(JWorld world, IRenderStrategy renderStrategy) {
        InstanceManager<AWorldObject> worldManager = world.getInstances();
        worldManager.resetIterator();
        
        AWorldObject instance;
        while( (instance = worldManager.nextActiveInstance()) != null )
        instance.render(renderStrategy);
        
        renderStrategy.newBuffer();
    }
}
