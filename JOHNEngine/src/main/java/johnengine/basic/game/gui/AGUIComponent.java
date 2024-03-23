package johnengine.basic.game.gui;

import johnengine.basic.game.AWorldObject;

public abstract class AGUIComponent extends AWorldObject {
    
    protected int column;
    protected int row;
    protected int columnSpan;
    protected int rowSpan;
    protected CEventListener eventListener;

    public AGUIComponent(JGUI gui) {
        super(gui);
        this.eventListener = new CEventListener();
        this.column = 0;
        this.row = 0;
        this.columnSpan = 0;
        this.rowSpan = 0;
    }
    
    
    void setCellDimensions(int column, int row, int columnSpan, int rowSpan) {
        this.column = column;
        this.row = row;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
    }
    
    
    public void setEventListener(CEventListener eventListener) {
        this.eventListener = eventListener;
    }
}
