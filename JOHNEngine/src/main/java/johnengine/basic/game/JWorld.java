package johnengine.basic.game;

import java.util.ArrayList;
import java.util.List;

import johnengine.basic.InstanceManager;
import johnengine.basic.game.physics.IHasPhysics;
import johnengine.core.AGame;
import johnengine.core.ITickable;

public class JWorld extends AGameObject {
    
    /******************** PhysicsWorld-class ********************/
    public static class PhysicsWorld implements ITickable {
        private final List<IHasPhysics> physicsObjects;
        
        public PhysicsWorld() {
            this.physicsObjects = new ArrayList<>();
        }
        
        
        @Override
        public void tick(float deltaTime) {
            for( IHasPhysics physicsObject : this.physicsObjects )
            physicsObject.getPhysics().update(deltaTime, null);
        }
        
        public void addObject(IHasPhysics physicsObject) {
            this.physicsObjects.add(physicsObject);
        }
    }
    
    
    /******************** JWorld-class ********************/
    
    private final InstanceManager<AWorldObject> worldManager;
    private PhysicsWorld physicsRepresentation;
    
    public JWorld(AGame game) {
        super(game);
        
            // WorldManager must be instantiated before resetting the render strategy
            // as the DefaultRenderStrategy must be instantiated with the WorldManager
        this.worldManager = new InstanceManager<>();
        this.physicsRepresentation = new PhysicsWorld();
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
        
            // Physics tick
        this.physicsRepresentation.tick(deltaTime);
    }
    
    public void createInstance(AWorldObject instance) {
        this.worldManager.addInstance(instance);
    }
    
    public void createPhysicsObject(IHasPhysics physicsObject) {
        this.physicsRepresentation.addObject(physicsObject);
    }
    
    public void destroyInstance(AWorldObject instance) {
        this.worldManager.deleteInstance(instance.getID());
    }
    
    public void destroyInstance(long instanceID) {
        this.worldManager.deleteInstance(instanceID);
    }
    
    
    public InstanceManager<AWorldObject> getInstances() {
        return this.worldManager;
    }
}
