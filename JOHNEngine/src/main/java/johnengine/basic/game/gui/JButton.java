package johnengine.basic.game.gui;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.game.components.CMouseListener;

public class JButton extends AGUIComponent {

    private JText textElement;
    private Mesh DEBUGmesh;
    private CMouseListener mouseListener;
    
    public JButton(JGUI gui, String caption, Mesh DEBUGmesh) {
        super(gui);
        this.addComponentAndFinalize(new JText(gui, caption), 0, 0, 1, 1);
        this.DEBUGmesh = DEBUGmesh;
        this.mouseListener = null;
    }

    
    @Override
    public void tick(float deltaTime) {
        this.mouseListener.tick(deltaTime);
    }
    
    
    public void setCaption(String caption) {
        this.textElement.setTextString(caption);
    }
    
    public void setMouseListener(CMouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }
    
    
    public String getCaption() {
        return this.textElement.getTextString();
    }
    
    public Mesh DEBUGgetMesh() {
        return this.DEBUGmesh;
    }
}
