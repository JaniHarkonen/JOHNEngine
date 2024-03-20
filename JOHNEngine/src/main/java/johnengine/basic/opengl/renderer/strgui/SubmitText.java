package johnengine.basic.opengl.renderer.strgui;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.gui.CText;
import johnengine.core.renderer.IRenderSubmissionStrategy;

public class SubmitText implements IRenderSubmissionStrategy<CText> {

    private final GUIRenderPass strategy;
    
    SubmitText(GUIRenderPass strategy) {
        this.strategy = strategy;
    }
    
    
    @Override
    public void execute(CText target) {
        CTransform transform = target.getTransform();
        Vector3f position = transform.getPosition().get();
        
        this.strategy.addRenderUnit(new RenderUnit(
            target.getText(),
            target.getFont(),
            position.x,
            position.y
        ));
    }
}
