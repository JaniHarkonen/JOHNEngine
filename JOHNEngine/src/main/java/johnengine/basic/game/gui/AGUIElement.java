package johnengine.basic.game.gui;

import java.util.List;

import johnengine.basic.assets.font.Font;
import johnengine.basic.game.AGameObject;
import johnengine.core.AGame;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderPass;

public abstract class AGUIElement 
    extends AGameObject 
    implements IRenderable
{
    protected Font font;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected int columns;
    protected int rows;
    
    public AGUIElement(AGame game, int columns, int rows) {
        super(game);
        this.font = null;
        this.x = 0.0f;
        this.y = 0.0f;
        this.width = 0.0f;
        this.height = 0.0f;
        this.columns = columns;
        this.rows = rows;
    }
    
    public void updateChildCoordinates() {
        float cellWidth = this.width / this.columns;
        float cellHeight = this.height / this.rows;
        
        for( AGUIComponent child : this.getChildren() )
        {
            child.configure(
                this.x + cellWidth * child.getColumn(), 
                this.y + cellHeight * child.getRow(), 
                cellWidth * child.getColumnSpan(), 
                cellHeight * child.getRowSpan(), 
                child.getColumnCount(), 
                child.getRowCount()
            );
            
            child.updateChildCoordinates();
        }
    }
    
    @Override
    public void submit(IRenderPass renderPass) {
        renderPass.executeSubmissionStrategy(this);
        //for( AGUIComponent child : this.getChildren() )
        //child.submit(renderPass);
    }
    
    
    void configure(
        float x,
        float y,
        float width,
        float height,
        int columns,
        int rows
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.columns = columns;
        this.rows = rows;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    
    public abstract List<AGUIComponent> getChildren();
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public int getColumnCount() {
        return this.columns;
    }
    
    public int getRowCount() {
        return this.rows;
    }
    
    public Font getFont() {
        return this.font;
    }
}
