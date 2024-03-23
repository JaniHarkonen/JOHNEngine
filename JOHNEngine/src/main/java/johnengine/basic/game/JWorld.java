package johnengine.basic.game;

import johnengine.basic.InstanceManager;
import johnengine.core.AGame;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderContext;

public class JWorld extends AGameObject implements IWorld, IRenderContext {
    private final InstanceManager<AWorldObject> worldManager;
    
    public JWorld(AGame game) {
        super(game);
        
            // WorldManager must be instantiated before resetting the render strategy
            // as the DefaultRenderStrategy must be instantiated with the WorldManager
        this.worldManager = new InstanceManager<>();
    }
    
    
    @Override
    public void tick(float deltaTime) {
        this.worldManager.processRequests();
        this.worldManager.resetIterator();
        
            // Use InstanceManager's iterator instead of requesting a 
            // list of all available instances
        AWorldObject inst;
        while( (inst = this.worldManager.nextInstance()) != null )
        inst.tick(deltaTime);
    }
    
    public void createInstance(AWorldObject instance) {
        this.worldManager.addInstance(instance);
    }
    
    public void destroyInstance(AWorldObject instance) {
        this.worldManager.deleteInstance(instance.getID());
    }
    
    public void destroyInstance(long instanceID) {
        this.worldManager.deleteInstance(instanceID);
    }
    
    
    /*public InstanceManager<AWorldObject> getInstances() {
        return this.worldManager;
    }*/
    
    @Override
    public void startRenderBuffer() {
        this.worldManager.resetIterator();
    }
    
    @Override
    public IRenderable nextInstance() {
        return this.worldManager.nextInstance();
    }
}
