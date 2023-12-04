package johnengine.basic.game;

import johnengine.basic.InstanceManager;
import johnengine.core.AGame;

public class JWorld extends AGameObject {
    private final InstanceManager<AWorldObject> worldManager;
    private JCamera activeCamera;
    
    public JWorld(AGame game) {
        super(game);
        
            // WorldManager must be instantiated before resetting the render strategy
            // as the DefaultRenderStrategy must be instantiated with the WorldManager
        this.worldManager = new InstanceManager<>();
        this.activeCamera = null;
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
    
    public void setActiveCamera(JCamera activeCamera) {
        this.activeCamera = activeCamera;
    }
    
    
    public InstanceManager<AWorldObject> getInstances() {
        return this.worldManager;
    }
}
