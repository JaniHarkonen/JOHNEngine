package johnengine.basic.renderer.strvaochc.rewrite;

import org.joml.Vector3f;

import johnengine.basic.renderer.asset.rewrite.Mesh;
import johnengine.basic.renderer.asset.rewrite.Texture;

public class RenderUnit {
    private Mesh mesh;
    private Texture texture;
    private Vector3f position;
    
    public RenderUnit(Mesh mesh, Texture texture, Vector3f position) {
        this.mesh = mesh;
        this.texture = texture;
        this.position = position;
    }
    
    
    Mesh getMesh() {
        return this.mesh;
    }
    
    Texture getTexture() {
        return this.texture;
    }
    
    Vector3f getPosition() {
        return this.position;
    }
}
