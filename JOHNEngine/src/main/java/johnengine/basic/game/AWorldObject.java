package johnengine.basic.game;

import java.util.ArrayList;
import java.util.List;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderPass;

public abstract class AWorldObject extends AGameObject implements 
    IRenderable, 
    ISceneGraphParent, 
    ISceneGraphChild
{
    
    protected JWorld world;
    protected boolean isVisible;
    protected CTransform transform;
    protected AWorldObject parent;
    protected List<ISceneGraphChild> children;

    public AWorldObject(JWorld world) {
        super(world.getGame());
        this.world = world;
        this.isVisible = true;
        this.transform = new CTransform();
        this.parent = null;
        this.children = new ArrayList<>();
    }
    
    
    @Override
    public void submit(IRenderPass renderPass) {
        for( ISceneGraphChild child : this.children )
        child.submit(renderPass);
        
        renderPass.executeSubmissionStrategy(this);
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
    }
    
    @Override
    public void detached() {
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
    
    public AWorldObject getParent() {
        return this.parent;
    }
    
    public CTransform getTransform() {
        return this.transform;
    }
}
