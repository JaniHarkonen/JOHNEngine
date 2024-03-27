package johnengine.basic.opengl.renderer.gui;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.gui.JForm;

public class SubmitForm extends AGUISubmission<JForm> {
    
    private SubmitForm(Font font) {
        super(font, 0, 0, 0, 0);
    }
    
    SubmitForm(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JForm target) {
        this.renderPass
        .getCurrentDOM()
        .addNode(new SubmitForm(target.getFont()));
    }

    @Override
    public void render(RendererContext context) {
        
    }
}
