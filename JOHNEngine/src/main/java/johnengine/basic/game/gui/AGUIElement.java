package johnengine.basic.game.gui;

import java.util.List;

import org.joml.Vector4f;

import johnengine.Defaults;
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
    protected Vector4f color;
    protected Vector4f textColor;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected int columns;
    protected int rows;
    
    public AGUIElement(AGame game, int columns, int rows) {
        super(game);
        this.font = null;
        this.color = Defaults.DEFAULT_GUI_ELEMENT_COLOR;
        this.textColor = Defaults.DEFAULT_GUI_TEXT_COLOR;
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
    public void tick(float deltaTime) {
        for( AGUIComponent child : this.getChildren() )
        child.tick(deltaTime);
    }
    
    @Override
    public void submit(IRenderPass renderPass) {
        renderPass.executeSubmissionStrategy(this);
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
    
    public void setColor(Vector4f color) {
        this.color = color;
    }
    
    public void setTextColor(Vector4f textColor) {
        this.textColor = textColor;
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
    
    public Vector4f getColor() {
        return this.color;
    }
    
    public Vector4f getTextColor() {
        return this.textColor;
    }
}
