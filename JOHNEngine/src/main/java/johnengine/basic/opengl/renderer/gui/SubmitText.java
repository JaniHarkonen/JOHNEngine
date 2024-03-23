package johnengine.basic.opengl.renderer.gui;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.gui.CText;
import johnengine.core.renderer.IRenderSubmissionStrategy;

public class SubmitText implements IRenderSubmissionStrategy<CText> {

    private final GUIRenderPass renderPass;
    
    SubmitText(GUIRenderPass renderPass) {
        this.renderPass = renderPass;
    }
    
    
    @Override
    public void execute(CText target) {
        CTransform transform = target.getTransform();
        Vector3f position = transform.getPosition().get();
        
        this.renderPass
        .getCurrentRenderBuffer()
        .addRenderElement(
            new RenderElement(
            target.getText(),
            target.getFont(),
            position.x,
            position.y
        ));
    }
}
