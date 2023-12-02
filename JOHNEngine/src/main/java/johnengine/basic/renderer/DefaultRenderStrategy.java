package johnengine.basic.renderer;

import johnengine.basic.InstanceManager;
import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.core.IRenderBufferStrategy;

public class DefaultRenderStrategy implements IRenderStrategy {
    
    @Override
    public void execute(JWorld world, IRenderBufferStrategy renderBufferStrategy) {
        renderBufferStrategy.startBuffer();
        
        InstanceManager<AWorldObject> worldManager = world.getInstances();
        worldManager.resetIterator();
        
        AWorldObject instance;
        while( (instance = worldManager.nextActiveInstance()) != null )
        instance.render(renderBufferStrategy);
        
        renderBufferStrategy.endBuffer();
    }
}
