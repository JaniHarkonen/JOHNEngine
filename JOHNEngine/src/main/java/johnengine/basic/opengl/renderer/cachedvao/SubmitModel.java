package johnengine.basic.opengl.renderer.cachedvao;

import org.joml.Matrix4f;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.game.components.CModel;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;

public class SubmitModel extends ACachedVAOSubmission<CModel> {

    public SubmitModel(CachedVAORenderPass renderPass) {
        super(renderPass);
    }

    @Override
    public void execute(CModel instance) {
        Mesh mesh = instance.getMesh();
        
        RenderUnit unit = new RenderUnit(
            (MeshGraphicsGL) mesh.getGraphicsStrategy(), 
            mesh.getInfo().getAsset().get(),
            mesh.getMaterial(),
            new Matrix4f(instance.getTransform().get())
        );
        
        this.renderPass.getCurrentRenderBuffer().addRenderUnit(unit);
    }
}
