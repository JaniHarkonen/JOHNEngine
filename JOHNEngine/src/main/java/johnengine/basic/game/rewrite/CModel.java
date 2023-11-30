package johnengine.basic.game.rewrite;

import johnengine.basic.game.CPosition;
import johnengine.basic.renderer.asset.rewrite.Mesh;
import johnengine.basic.renderer.asset.rewrite.Texture;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.IDrawable;

public class CModel implements IDrawable {
    
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
