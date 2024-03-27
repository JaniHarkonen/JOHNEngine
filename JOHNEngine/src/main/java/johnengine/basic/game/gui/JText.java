package johnengine.basic.game.gui;

import johnengine.basic.assets.font.Font;

public class JText extends AGUIComponent {

    private Font font;
    private String textString;
    
    public JText(
        JGUI gui, 
        Font font, 
        String textString
    ) {
        super(gui);
        this.font = font;
        this.textString = textString;
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setTextString(String textString) {
        this.textString = textString;
    }
    
    
    public Font getFont() {
        return this.font;
    }
    
    public String getTextString() {
        return this.textString;
    }
}
