package johnengine.basic.opengl.renderer.gui;

import org.joml.Vector4f;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.gui.JFrame;

public class SubmitFrame extends AGUISubmission<JFrame> {
    
    private SubmitFrame(
        Font font,
        Vector4f color,
        Vector4f textColor
    ) {
        super(font, color, textColor, 0, 0, 0, 0);
    }
    
    SubmitFrame(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JFrame target) {
        this.renderPass
        .getCurrentDOM()
        .addFrame(new SubmitFrame(
            target.getFont(),
            target.getColor(),
            target.getTextColor()
        ));
    }
    
    @Override
    public void render(RendererContext context) {
        
    }
}
