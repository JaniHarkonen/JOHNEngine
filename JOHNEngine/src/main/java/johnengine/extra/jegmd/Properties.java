package johnengine.extra.jegmd;

import java.util.HashSet;
import java.util.Set;

public class Properties {
    public static final String X = "x";
    public static final String Y = "y";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String COLUMN = "col";
    public static final String ROW = "row";
    public static final String COLUMN_SPAN = "colSpan";
    public static final String ROW_SPAN = "rowSpan";
    public static final String COLUMNS = "cols";
    public static final String ROWS = "rows";
    public static final String CAPTION = "caption";
    public static final String COLOR = "color";
    public static final String TEXT_COLOR = "textColor";
    public static final String CONTENT = "content";
    
    private static final Set<String> PROPERTIES;
    static {
        PROPERTIES = new HashSet<>();
        PROPERTIES.add(WIDTH);
        PROPERTIES.add(HEIGHT);
        PROPERTIES.add(COLUMN);
        PROPERTIES.add(ROW);
        PROPERTIES.add(COLUMN_SPAN);
        PROPERTIES.add(ROW_SPAN);
        PROPERTIES.add(COLUMNS);
        PROPERTIES.add(ROWS);
        PROPERTIES.add(CAPTION);
        PROPERTIES.add(COLOR);
        PROPERTIES.add(TEXT_COLOR);
        PROPERTIES.add(CONTENT);
    }
    
    public static boolean isProperty(String property) {
        return (PROPERTIES.contains(property));
    }
}
