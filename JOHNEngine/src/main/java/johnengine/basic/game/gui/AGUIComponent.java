package johnengine.basic.game.gui;

import java.util.List;

import johnengine.basic.NodeManager;

public abstract class AGUIComponent extends AGUIElement {
    protected NodeManager<AGUIElement, AGUIComponent> nodeManager;
    protected int column;
    protected int row;
    protected int columnSpan;
    protected int rowSpan;

    public AGUIComponent(JGUI gui, int columns, int rows) {
        super(gui.getGame(), columns, rows);
        this.nodeManager = new NodeManager<>();
        this.column = 0;
        this.row = 0;
        this.columnSpan = 0;
        this.rowSpan = 0;
    }
    
    public AGUIComponent(JGUI gui) {
        this(gui, 1, 1);
    }
    
    
    public AGUIComponent addComponent(
        AGUIComponent component,
        int column,
        int row,
        int columnSpan,
        int rowSpan
    ) {
        component.configureCells(column, row, columnSpan, rowSpan);
        this.nodeManager.addChild(component);
        component.setParent(this);
        return this;
    }
    
    public void addComponentAndFinalize(
        AGUIComponent component,
        int column,
        int row,
        int columnSpan,
        int rowSpan
    ) {
        this.addComponent(component, column, row, columnSpan, rowSpan);
        this.updateChildCoordinates();
    }
    
    void configureCells(
        int column,
        int row,
        int columnSpan,
        int rowSpan
    ) {
        this.column = column;
        this.row = row;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
    }
    
    void setParent(AGUIElement parent) {
        this.nodeManager.setParent(parent);
    }
    
    public void setGridDimensions(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }
    
    
    public int getColumn() {
        return this.column;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getColumnSpan() {
        return this.columnSpan;
    }
    
    public int getRowSpan() {
        return this.rowSpan;
    }
    
    public int getColumnCount() {
        return this.columns;
    }
    
    public int getRowCount() {
        return this.rows;
    }
    
    @Override
    public List<AGUIComponent> getChildren() {
        return this.nodeManager.getChildren();
    }
}
