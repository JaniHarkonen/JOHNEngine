package johnengine.basic.game;

import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.Texture;
import johnengine.core.IRenderBufferStrategy;
import johnengine.core.IRenderable;

public class CModel implements IRenderable {
    
    private Mesh mesh;
    private Texture texture;
    private CPosition position;
    private CRotation rotation;
    private CScale scale;

    
    public CModel(CPosition position, CRotation rotation, CScale scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = new Mesh("default");
        this.texture = new Texture("default");
    }
    
    public CModel() {
        this(new CPosition(), new CRotation(), new CScale());
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
    
    public CRotation getRotation() {
        return this.rotation;
    }
    
    public CScale getScale() {
        return this.scale;
    }
    
    public void setPosition(CPosition position) {
        this.position = position;
    }
    
    public void setRotation(CRotation rotation) {
        this.rotation = rotation;
    }
    
    public void setScale(CScale scale) {
        this.scale = scale;
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
