package johnengine.basic.game.gui;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.ISceneGraphChild;
import johnengine.basic.game.ISceneGraphParent;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.opengl.input.MouseKeyboardInputGL;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderPass;

public class CText implements IRenderable, ISceneGraphChild {
    
    private String text;
    private Font font;
    private CTransform transform;
    private MouseKeyboardInputGL DEBUGInput;
    
    public CText(String text, MouseKeyboardInputGL DEBUGInput) {
        this.text = text;
        this.font = null;
        this.transform = new CTransform();
        this.DEBUGInput = DEBUGInput;
    }
    
    
    @Override
    public void submit(IRenderPass renderPass) {
        renderPass.executeSubmissionStrategy(this);
    }

    @Override
    public void attached(ISceneGraphParent parent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void detached() {
        // TODO Auto-generated method stub
        
    }
    
    
    /*********************** GETTERS ***********************/
    
    public String getText() {
        //this.text = this.DEBUGInput.getState().getKeyboardString() + "<";
        return this.text;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public CTransform getTransform() {
        return this.transform;
    }
    
    
    /*********************** SETTERS ***********************/
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
}
