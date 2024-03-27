package johnengine.basic.opengl.renderer.gui;

import johnengine.basic.game.gui.JForm;

public class SubmitForm extends AGUISubmission<JForm> {
    
    private SubmitForm(
    ) {
        super(0, 0, 0, 0);
    }
    
    SubmitForm(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JForm target) {
        this.renderPass
        .getCurrentDOM()
        .addNode(new SubmitForm());
    }

    @Override
    public void render(RendererContext context) {
        
    }
}
