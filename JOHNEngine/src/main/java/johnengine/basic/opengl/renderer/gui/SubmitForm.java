package johnengine.basic.opengl.renderer.gui;

import org.joml.Vector4f;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.gui.JForm;

public class SubmitForm extends AGUISubmission<JForm> {
    
    private SubmitForm(
        Font font,
        Vector4f color,
        Vector4f textColor
    ) {
        super(font, color, textColor, 0, 0, 0, 0);
    }
    
    SubmitForm(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JForm target) {
        this.renderPass
        .getCurrentDOM()
        .addNode(new SubmitForm(
            target.getFont(),
            target.getColor(),
            target.getTextColor()
        ));
    }

    @Override
    public void render(RendererContext context) {
        
    }
}
