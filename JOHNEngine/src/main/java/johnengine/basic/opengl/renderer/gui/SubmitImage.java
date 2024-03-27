package johnengine.basic.opengl.renderer.gui;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.game.gui.JImage;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.vao.VAO;

public class SubmitImage extends AGUISubmission<JImage> {
    
    private Mesh mesh;
    private Texture texture;
    
    private SubmitImage(
        Font font,
        Mesh mesh,
        Texture texture,
        float x,
        float y,
        float width,
        float height
    ) {
        super(font, x, y, width, height);
        this.mesh = mesh;
        this.texture = texture;
    }
    
    SubmitImage(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JImage target) {
        this.renderPass
        .getCurrentDOM()
        .addNode(new SubmitImage(
            target.getFont(),
            target.getMesh(),
            target.getTexture(),
            target.getX(),
            target.getY(),
            target.getWidth(),
            target.getHeight()
        ));
    }

    @Override
    public void render(RendererContext context) {
        Matrix4f modelMatrix = new Matrix4f()
        .translate(this.x, this.y, 0)
        .scale(this.width, this.height, 0);
        
        ((UNIMatrix4f) context.shaderProgram.getUniform("modelMatrix"))
        .set(modelMatrix);
        
            // Bind texture
        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        TextureGraphicsGL textureGL = 
            (TextureGraphicsGL) this.texture.getGraphicsStrategy();
        textureGL.bind();
        
            // Bind mesh
        MeshGraphicsGL meshGL = (MeshGraphicsGL) this.mesh.getGraphicsStrategy();
        VAO vao = context.vaoCache.fetchVAO(meshGL);
        vao.bind();
        
            // Draw
        GL46.glDrawElements(
            GL46.GL_TRIANGLES, 
            this.mesh.getInfo().getAsset().get().getVertexCount() * 3, 
            GL46.GL_UNSIGNED_INT, 
            0
        );
    }
}
