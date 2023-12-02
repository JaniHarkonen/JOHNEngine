package johnengine.basic.game;

import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.Texture;
import johnengine.core.IRenderBufferStrategy;
import johnengine.core.IRenderable;

public class CModel implements IRenderable {
    
    private Mesh mesh;
    private Texture texture;
    private CPosition position;

    
    public CModel(CPosition position) {
        this.position = position;
        this.mesh = new Mesh("default");
        this.texture = new Texture("default");
    }
    
    public CModel() {
        this(new CPosition());
    }
    
    
    @Override
    public void render(IRenderBufferStrategy renderBufferStrategy) {
        renderBufferStrategy.executeStrategoid(this);
    }
    
    
    public Mesh getMesh() {
        return this.mesh;
    }
    
    public Texture getTexture() {
        return this.texture;
    }
    
    public CPosition getPosition() {
        return this.position;
    }
    
    public void setPosition(CPosition position) {
        this.position = position;
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
