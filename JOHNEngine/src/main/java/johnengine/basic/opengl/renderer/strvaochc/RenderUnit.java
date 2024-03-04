package johnengine.basic.opengl.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;

public class RenderUnit {
    private Mesh mesh;
    private Texture texture;
    private Matrix4f positionMatrix;
    
    public RenderUnit(Mesh mesh, Texture texture, Matrix4f positionMatrix) {
        this.mesh = mesh;
        this.texture = texture;
        this.positionMatrix = positionMatrix;
    }
    
    
    Mesh getMesh() {
        return this.mesh;
    }
    
    Texture getTexture() {
        return this.texture;
    }
    
    Matrix4f getPositionMatrix() {
        return this.positionMatrix;
    }
}
