package johnengine.basic.game;

import johnengine.basic.InstanceManager;
import johnengine.core.AGame;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.IDrawable;

public class JWorld extends AGameObject implements IDrawable {

    private final InstanceManager<AWorldObject> worldManager;
    
    public JWorld(AGame game, long id) {
        super(game, id);
        this.worldManager = new InstanceManager<>();
    }
    
    public JWorld(AGame game) {
        this(game, 0);
    }

    
    @Override
    public void render(ARenderer renderer) {
        
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
        instance.setID(this.worldManager.nextUniqueID());
        this.worldManager.addInstance(instance);
    }
    
    public void destroyInstance(AWorldObject instance) {
        this.worldManager.deleteInstance(instance.getID());
    }
    
    public void destroyInstance(long instanceID) {
        this.worldManager.deleteInstance(instanceID);
    }
}
