package johnengine.basic.opengl.renderer.gui;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.game.gui.JText;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.vao.VAO;

public class SubmitText extends AGUISubmission<JText> {
    
    private String textString;
    
    private SubmitText(
        Font font,
        String textString,
        float x,
        float y,
        float width,
        float height
    ) {
        super(font, x, y, width, height);
        this.textString = textString;
    }
    
    SubmitText(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JText target) {
        this.renderPass
        .getCurrentDOM()
        .addNode(new SubmitText(
            target.getFont(),
            target.getTextString(),
            target.getX(),
            target.getY(),
            target.getWidth(),
            target.getHeight()
        ));
    }

    @Override
    public void render(RendererContext context) {
        UNIMatrix4f modelMatrixUniform = 
            ((UNIMatrix4f) context.shaderProgram.getUniform("modelMatrix"));
        
        Font font = context.font;
        float lineHeight = 22;  // these should be provided by renderer context
        float baseLine = 16;  // these should be provided by renderer context
        String[] lines = this.textString.split("\n");
        
        float textY = this.y;
        for( int i = 0; i < lines.length; i++ )
        {
            float textX = this.x;
            String text = lines[i];
            for( int j = 0; j < text.length(); j++ )
            {
                char character = text.charAt(j);
                Font.Glyph glyph = font.getGlyph(character);
                Mesh mesh = glyph.getMesh();
                
                    // Determine text offset
                Matrix4f modelMatrix = new Matrix4f()
                .translate(textX, textY + baseLine - glyph.getOriginY(), 0)
                .scale(1, 1, 0);
                modelMatrixUniform.set(modelMatrix);
                
                    // Bind texture
                GL46.glActiveTexture(GL46.GL_TEXTURE0);
                TextureGraphicsGL textureGL = 
                    (TextureGraphicsGL) font.getTexture().getGraphicsStrategy();
                textureGL.bind();
                
                    // Bind VAO
                MeshGraphicsGL meshGL = (MeshGraphicsGL) mesh.getGraphicsStrategy();
                VAO vao = context.vaoCache.fetchVAO(meshGL);
                vao.bind();
                
                GL46.glDrawElements(
                    GL46.GL_TRIANGLES, 
                    mesh.getInfo().getAsset().get().getVertexCount() * 3, 
                    GL46.GL_UNSIGNED_INT, 
                    0
                );
                
                textX += glyph.getWidth();
            }
            
            textY += lineHeight;
        }
    }
}
