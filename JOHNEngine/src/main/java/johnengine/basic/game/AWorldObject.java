package johnengine.basic.game;

import johnengine.core.renderer.IDrawable;

public abstract class AWorldObject extends AGameObject implements IDrawable {
    
    protected JWorld world;
    protected boolean isVisible;

    protected AWorldObject(JWorld world) {
        super(world.getGame(), 0);
        this.world = world;
        this.isVisible = true;
    }
    
    
    @Override
    public void destroy() {
        super.destroy();
        
        if( this.world != null )
        this.world.destroyInstance(this);
    }
    
    
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    
    public void hide() {
        this.isVisible = false;
    }
    
    public void show() {
        this.isVisible = true;
    }
    
    public JWorld getWorld() {
        return this.world;
    }
    
    public boolean isVisible() {
        return this.isVisible;
    }
}
