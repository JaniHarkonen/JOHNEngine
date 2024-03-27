package johnengine.basic.game.gui;

public class JText extends AGUIComponent {

    private String textString;
    
    public JText(
        JGUI gui, 
        String textString
    ) {
        super(gui);
        this.textString = textString;
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    
    public void setTextString(String textString) {
        this.textString = textString;
    }
    
    
    public String getTextString() {
        return this.textString;
    }
}
