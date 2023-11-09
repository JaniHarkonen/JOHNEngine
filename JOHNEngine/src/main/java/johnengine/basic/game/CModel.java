package johnengine.basic.game;

import johnengine.basic.assets.imgasset.Texture;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.IDrawable;

public class CModel implements IDrawable {
    
    private Mesh mesh;
    private Texture texture;

    
    @Override
    public void render(ARenderer renderer) {
        this.texture.render(renderer);
        this.mesh.render(renderer);
    }
    
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
