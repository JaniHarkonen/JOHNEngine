package johnengine.basic.game;

import johnengine.basic.InstanceManager;
import johnengine.basic.renderer.ARenderStrategy;
import johnengine.core.AGame;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.IDrawable;

public class JWorld extends AGameObject implements IDrawable {

    public static class DefaultRenderStrategy extends ARenderStrategy<JWorld> {

        private InstanceManager<AWorldObject> worldManager;
        private JCamera camera;
        
        public DefaultRenderStrategy(InstanceManager<AWorldObject> worldManager) {
            this.worldManager = worldManager;
        }
        
        
        @Override
        public void render(ARenderer renderer) {
            this.worldManager.resetIterator();
            this.camera.render(renderer);
            
                // Use InstanceManager's iterator instead of requesting a 
                // list of all available instances
            AWorldObject inst;
            while( (inst = this.worldManager.nextInstance()) != null )
            inst.render(renderer);
        }
        
        
        public void setCamera(JCamera camera) {
            this.camera = camera;
        }
    }
    
    
    /********************* JWorld-class *********************/
    
    private final InstanceManager<AWorldObject> worldManager;
    private ARenderStrategy<JWorld> renderStrategy;
    private JCamera activeCamera;
    
    public JWorld(AGame game, long id) {
        super(game, id);
        
            // WorldManager must be instantiated before resetting the render strategy
            // as the DefaultRenderStrategy must be instantiated with the WorldManager
        this.worldManager = new InstanceManager<>();
        this.activeCamera = null;
        this.resetRenderStrategy();
    }
    
    public JWorld(AGame game) {
        this(game, 0);
    }

    
    @Override
    public void render(ARenderer renderer) {
        DefaultRenderStrategy.class.cast(this.renderStrategy).setCamera(this.activeCamera);
        this.renderStrategy.render(renderer);
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
    
    public void resetRenderStrategy() {
        this.renderStrategy = new DefaultRenderStrategy(this.worldManager);
    }
    
    public void setActiveCamera(JCamera activeCamera) {
        this.activeCamera = activeCamera;
    }
}
