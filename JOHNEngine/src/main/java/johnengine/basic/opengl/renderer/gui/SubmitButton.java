package johnengine.basic.opengl.renderer.gui;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.game.gui.JButton;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.uniforms.UNIInteger;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.uniforms.UNIVector4f;
import johnengine.basic.opengl.renderer.vao.VAO;

public class SubmitButton extends AGUISubmission<JButton> {
    
    private Mesh mesh;
    
    private SubmitButton(
        Font font,
        Vector4f color,
        Vector4f textColor,
        Mesh mesh,
        float x,
        float y,
        float width,
        float height
    ) {
        super(font, color, textColor, x, y, width, height);
        this.mesh = mesh;
    }
    
    SubmitButton(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JButton target) {
        this.renderPass
        .getCurrentDOM()
        .addNode(new SubmitButton(
            target.getFont(),
            target.getColor(),
            target.getTextColor(),
            target.DEBUGgetMesh(),
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
        
        ((UNIInteger) context.shaderProgram.getUniform("hasTexture"))
        .set(0);
        
        ((UNIVector4f) context.shaderProgram.getUniform("elementColor"))
        .set(context.color);
        
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
