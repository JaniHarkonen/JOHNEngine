package johnengine.basic.game.components;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.ISceneGraphChild;
import johnengine.basic.game.ISceneGraphParent;
import johnengine.basic.game.components.geometry.CRotation;
import johnengine.basic.game.components.geometry.rewrite.CPosition;
import johnengine.basic.game.components.geometry.rewrite.CScale;
import johnengine.basic.game.components.geometry.rewrite.CTransform;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.Texture;
import johnengine.core.renderer.IRenderStrategy;

public class CModel implements ISceneGraphChild {
    
    private Mesh mesh;
    private Texture texture;
    private CTransform transform;

    
    public CModel(CPosition position, CRotation rotation, CScale scale) {
        this.transform = new CTransform();
        this.mesh = new Mesh("default");
        this.texture = new Texture("default");
        //this.getTransform().setScale(new Vector3f(0.25f, 0.25f, 0.25f));
        //this.getTransform().setPosition(new Vector3f(10.5f, 10.5f, 10.5f));
        //this.getTransform().setRotation(new Vector3f(90, 0, 0));
        //this.getTransform().rotate(new Vector3f(0, 0, 0));  
    }
    
    public CModel() {
        this(new CPosition(), new CRotation(), new CScale());
    }
    
    
    @Override
    public void render(IRenderStrategy renderStrategy) {
        renderStrategy.executeStrategoid(this);
    }
    
    
    public Mesh getMesh() {
        return this.mesh;
    }
    
    public Texture getTexture() {
        return this.texture;
    }
        
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void attached(ISceneGraphParent parent) {
        this.transform.attachTo(((AWorldObject) parent).getTransform());
    }

    @Override
    public void detached() {
        this.transform.unparent();
    }
    
    public CTransform getTransform() {
        return this.transform;
    }
}
