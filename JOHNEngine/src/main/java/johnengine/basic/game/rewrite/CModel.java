package johnengine.basic.game.rewrite;

import johnengine.basic.renderer.rewrite.asset.Mesh;
import johnengine.basic.renderer.rewrite.asset.Texture;
import johnengine.core.renderer.rewrite.ARenderer;
import johnengine.core.rewrite.IDrawable;

public class CModel implements IDrawable {
    
    private Mesh mesh;
    private Texture texture;
    private CPosition position;

    
    public CModel(CPosition position) {
        this.position = position;
    }
    
    public CModel() {
        this.position = new CPosition();
    }
    

    @Override
    public void render(ARenderer renderer) {
        renderer.getRenderBufferStrategoid(this).execute(this);
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
