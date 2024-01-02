package johnengine.basic.game;

import java.util.ArrayList;
import java.util.List;

import johnengine.basic.game.components.geometry.rewrite.CTransform;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderStrategy;

public abstract class AWorldObject extends AGameObject implements 
    IRenderable, 
    ISceneGraphParent, 
    ISceneGraphChild
{
    
    protected JWorld world;
    protected boolean isVisible;
    //protected CPosition position;
    //protected CRotation rotation;
    //protected CScale scale;
    protected CTransform transform;
    protected AWorldObject parent;
    protected List<ISceneGraphChild> children;

    public AWorldObject(JWorld world) {
        super(world.getGame());
        this.world = world;
        this.isVisible = true;
        //this.position = new CPosition();
        //this.rotation = new CRotation();
        //this.scale = new CScale();
        this.transform = new CTransform();
        this.parent = null;
        this.children = new ArrayList<>();
    }
    
    
    @Override
    public void render(IRenderStrategy renderStrategy) {
        for( ISceneGraphChild child : this.children )
        child.render(renderStrategy);
        
        renderStrategy.executeStrategoid(this);
    }
    
    @Override
    public void destroy() {
        if( this.world != null )
        this.world.destroyInstance(this);
        
        super.destroy();
    }
    
    @Override
    public void attach(ISceneGraphChild child) {
        //ISceneGraphParent.super.attach(child);
        child.attached(this);
        this.children.add(child);
    }
    
    @Override
    public void detach(ISceneGraphChild child) {
        int s = this.children.size();
        for( int i = 0; i < s; i++ )
        {
            ISceneGraphChild currentChild = this.children.get(i);
            if( currentChild != child )
            continue;
            
            this.detach(currentChild, i);
            return;
        }
    }
    
    public void detach(int childIndex) {
        ISceneGraphChild child = this.children.get(childIndex);
        this.detach(child, childIndex);
    }
    
    public void detach(ISceneGraphChild child, int childIndex) {
        ISceneGraphParent.super.detach(child);
        this.children.remove(childIndex);
    }
    
    @Override
    public void attached(ISceneGraphParent parent) {
        this.parent = (AWorldObject) parent;
        this.transform.attachTo(this.parent.transform);
        //this.parent.getTransform().attach(this.transform);
        //this.position.setParent(this.parent.position);
        //this.scale.setParent(this.parent.scale);
    }
    
    @Override
    public void detached() {
        //this.position.unparent();
        //this.scale.unparent();
        
        //this.parent.getTransform().detach(this.transform);
        this.transform.unparent();
        this.parent = null;
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
    
    /*public CPosition getPosition() {
        return this.position;
    }
    
    public CRotation getRotation() {
        return this.rotation;
    }
    
    public CScale getScale() {
        return this.scale;
    }*/
    
    public AWorldObject getParent() {
        return this.parent;
    }
    
    public CTransform getTransform() {
        return this.transform;
    }
}
