package johnengine.basic.game;

import johnengine.core.renderer.IDrawable;

public abstract class AWorldObject extends AGameObject implements IDrawable {
    
    protected JWorld world;

    protected AWorldObject(JWorld world) {
        super(world.getGame(), 0);
        this.world = world;
    }
    
    
    @Override
    public void destroy() {
        super.destroy();
        
        if( this.world != null )
        this.world.destroyInstance(this);
    }
    
    
    public JWorld getWorld() {
        return this.world;
    }
}
