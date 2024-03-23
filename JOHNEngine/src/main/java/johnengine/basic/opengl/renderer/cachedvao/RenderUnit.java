package johnengine.basic.opengl.renderer.cachedvao;

import org.joml.Matrix4f;

import johnengine.basic.assets.mesh.MeshInfo;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;

public class RenderUnit {
    MeshGraphicsGL meshGraphics;
    MeshInfo.Data meshData;
    Material material;
    Matrix4f positionMatrix;
    
    public RenderUnit(
        MeshGraphicsGL meshGraphics,
        MeshInfo.Data meshData,
        Material material, 
        Matrix4f positionMatrix
    ) {
        this.meshGraphics = meshGraphics;
        this.meshData = meshData;
        this.material = material;
        this.positionMatrix = positionMatrix;
    }
}
