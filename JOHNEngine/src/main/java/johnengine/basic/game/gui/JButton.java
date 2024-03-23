package johnengine.basic.game.gui;

public class JButton extends AGUIComponent {

    private JText textElement;
    
    public JButton(JGUI gui, String caption) {
        super(gui);
        this.attach(new JText(gui, caption));
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    
    public void setCaption(String caption) {
        this.textElement.setTextString(caption);
    }
    
    
    public String getCaption() {
        return this.textElement.getTextString();
    }
}
