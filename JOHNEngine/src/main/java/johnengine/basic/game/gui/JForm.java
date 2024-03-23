package johnengine.basic.game.gui;

public class JForm extends AGUIComponent {
    
    private int columns;
    private int rows;
    
    public JForm(JGUI gui, int columns, int rows) {
        super(gui);
        this.columns = Math.max(1, columns);
        this.rows = Math.max(1, rows);
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    
    public AGUIComponent add(
        AGUIComponent component,
        int column,
        int row,
        int columnSpan,
        int rowSpan
    ) {
        this.attach(component);
        component.setCellDimensions(column, row, columnSpan, rowSpan);
        return component;
    }
    
    
    public int getColumnCount() {
        return this.columns;
    }
    
    public int getRowCount() {
        return this.rows;
    }
}
