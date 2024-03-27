package johnengine.basic.opengl.renderer.gui;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.gui.JFrame;

public class SubmitFrame extends AGUISubmission<JFrame> {
    
    private SubmitFrame(Font font) {
        super(font, 0, 0, 0, 0);
    }
    
    SubmitFrame(GUIRenderPass renderPass) {
        super(renderPass);
    }
    
    
    @Override
    public void execute(JFrame target) {
        this.renderPass
        .getCurrentDOM()
        .addFrame(new SubmitFrame(target.getFont()));
    }
    
    @Override
    public void render(RendererContext context) {
        
    }
}
