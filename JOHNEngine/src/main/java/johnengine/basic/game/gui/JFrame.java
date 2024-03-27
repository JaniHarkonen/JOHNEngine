package johnengine.basic.game.gui;

import java.util.List;

import johnengine.basic.NodeManager;

public class JFrame extends AGUIElement {
    
    private NodeManager<JGUI, AGUIComponent> nodeManager;
    
    public JFrame(JGUI gui, float x, float y, float width, float height) {
        super(gui.getGame(), 1, 1);
        this.nodeManager = new NodeManager<>();
        this.nodeManager.setParent(gui);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    public JFrame add(AGUIComponent component) {
        component.configureCells(0, 0, 1, 1);
        this.nodeManager.addChild(component);
        component.setParent(this);
        return this;
    }
    
    public void addAndFinalize(AGUIComponent component) {
        this.add(component);
        this.updateChildCoordinates();
    }
    
    
    @Override
    public List<AGUIComponent> getChildren() {
        return this.nodeManager.getChildren();
    }
}
