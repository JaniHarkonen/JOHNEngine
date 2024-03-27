package johnengine.basic.game;

import java.util.List;

import johnengine.basic.NodeManager;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderPass;

public abstract class AWorldObject extends AGameObject implements 
    IRenderable, 
    ISceneGraphParent, 
    ISceneGraphNode
{
    
    protected IWorld world;
    protected boolean isVisible;
    protected CTransform transform;
    protected NodeManager<AWorldObject, ISceneGraphNode> nodeManager;
    //protected AWorldObject parent;
    //protected List<ISceneGraphNode> children;

    public AWorldObject(IWorld world) {
        super(world.getGame());
        this.world = world;
        this.isVisible = true;
        this.transform = new CTransform();
        this.nodeManager = new NodeManager<>();
        //this.parent = null;
        //this.children = new ArrayList<>();
    }
    
    
    @Override
    public void submit(IRenderPass renderPass) {
        //for( ISceneGraphNode child : this.children )
        for( ISceneGraphNode child : this.nodeManager.getChildren() )
        child.submit(renderPass);
        
        renderPass.executeSubmissionStrategy(this);
    }
    
    @Override
    public void destroy() {
        /*if( this.world != null )
        this.world.destroyInstance(this);
        
        super.destroy();*/
    }
    
    @Override
    public void attach(ISceneGraphNode child) {
        child.attached(this);
        this.nodeManager.addChild(child);
        //this.children.add(child);
    }
    
    @Override
    public void detach(ISceneGraphNode child) {
        /*List<ISceneGraphNode> children = this.nodeManager.getChildren();
        int s = children.size();//this.children.size();
        for( int i = 0; i < s; i++ )
        {
            ISceneGraphNode currentChild = children.get(i);//this.children.get(i);
            if( currentChild != child )
            continue;
            
            this.detach(currentChild, i);
            return;
        }*/
        
        if( this.nodeManager.removeChild(child) )
        ISceneGraphParent.super.detach(child);
    }
    
    public void detach(int childIndex) {
        //ISceneGraphNode child = this.children.get(childIndex);
        //ISceneGraphNode child = this.nodeManager.getChild(childIndex);
        //this.detach(child, childIndex);
        
        ISceneGraphNode child = this.nodeManager.getChild(childIndex);
        
        if( child != null )
        this.detach(child);
        
        //if( this.nodeManager.removeChild(childIndex) != null )
        //ISceneGraphParent.super.detach(child);
    }
    
    /*public void detach(ISceneGraphNode child, int childIndex) {
        //ISceneGraphParent.super.detach(child);
        this.nodeManager.removeChild(child);
        //this.children.remove(childIndex);
    }*/
    
    @Override
    public void attached(ISceneGraphParent parent) {
        //this.parent = (AWorldObject) parent;
        AWorldObject parentWorldObject = (AWorldObject) parent;
        this.nodeManager.setParent(parentWorldObject);
        this.transform.attachTo(parentWorldObject.transform);
    }
    
    @Override
    public void detached() {
        this.transform.unparent();
        //this.parent = null;
        this.nodeManager.removeParent();
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
    
    public IWorld getWorld() {
        return this.world;
    }
    
    public boolean isVisible() {
        return this.isVisible;
    }
    
    public AWorldObject getParent() {
        //return this.parent;
        return this.nodeManager.getParent();
    }
    
    public CTransform getTransform() {
        return this.transform;
    }
    
    @Override
    public List<ISceneGraphNode> getChildren() {
        //return this.children;
        return this.getChildren();
    }
}
