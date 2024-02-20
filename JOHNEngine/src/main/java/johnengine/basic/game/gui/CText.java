package johnengine.basic.game.gui;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.ISceneGraphChild;
import johnengine.basic.game.ISceneGraphParent;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderStrategy;

public class CText implements IRenderable, ISceneGraphChild {
    
    private String text;
    private Font font;
    private CTransform transform;
    
    public CText(String text) {
        this.text = text;
        this.font = null;
        this.transform = new CTransform();
    }
    
    
    @Override
    public void render(IRenderStrategy renderStrategy) {
        renderStrategy.executeStrategoid(this);
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
