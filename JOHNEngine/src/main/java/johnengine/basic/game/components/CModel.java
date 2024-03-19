package johnengine.basic.game.components;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.ISceneGraphChild;
import johnengine.basic.game.ISceneGraphParent;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.renderer.IRenderPass;

public class CModel implements ISceneGraphChild {
    
    private Mesh mesh;
    private Texture texture;
    private CTransform transform;
    
    public CModel() {
        this.transform = new CTransform();
        this.mesh = null;
        this.texture = null;
    }
    
    
    @Override
    public void render(IRenderPass renderPass) {
        renderPass.executeStrategoid(this);
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
