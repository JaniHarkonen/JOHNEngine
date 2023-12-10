package johnengine.basic.game;

import johnengine.basic.game.components.geometry.CPosition;
import johnengine.basic.game.components.geometry.CRotation;
import johnengine.basic.game.components.geometry.CScale;
import johnengine.core.IRenderable;

public abstract class AWorldObject extends AGameObject implements IRenderable {
    
    protected JWorld world;
    protected boolean isVisible;
    protected CPosition position;
    protected CRotation rotation;
    protected CScale scale;

    protected AWorldObject(JWorld world) {
        super(world.getGame());
        this.world = world;
        this.isVisible = true;
        this.position = new CPosition();
        this.rotation = new CRotation();
        this.scale = new CScale();
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
    
    public CPosition getPosition() {
        return this.position;
    }
    
    public float getX() {
        return this.position.get().x;
    }
    
    public float getY() {
        return this.position.get().y;
    }
    
    public float getZ() {
        return this.position.get().z;
    }
    
    public CRotation getRotation() {
        return this.rotation;
    }
    
    public CScale getScale() {
        return this.scale;
    }
    
    public float getXScale() {
        return this.scale.getXScale();
    }
    
    public float getYScale() {
        return this.scale.getYScale();
    }
    
    public float getZScale() {
        return this.scale.getZScale();
    }
}
