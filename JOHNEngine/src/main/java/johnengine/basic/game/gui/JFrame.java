package johnengine.basic.game.gui;

import johnengine.basic.game.AWorldObject;
import johnengine.core.renderer.IRenderPass;

public class JFrame extends AWorldObject {
    
    private float width;
    private float height;
    
    public JFrame(JGUI gui, float width, float height) {
        super(gui);
        this.width = width;
        this.height = height;
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    @Override
    public void submit(IRenderPass renderPass) {
        renderPass.executeSubmissionStrategy(this);
    }
    
    
    public AGUIComponent add(AGUIComponent component) {
        this.attach(component);
        component.setCellDimensions(0, 0, 1, 1);
        return component;
    }
    
    
    public void setDimensions(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
}
