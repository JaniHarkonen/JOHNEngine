package johnengine.extra.jegmd;

import java.util.HashSet;
import java.util.Set;

public final class Elements {

    public static final String GUI = "gui";
    public static final String FRAME = "frame";
    public static final String FORM = "form";
    public static final String BUTTON = "button";
    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    
    private static final Set<String> ELEMENTS;
    static {
        ELEMENTS = new HashSet<>();
        ELEMENTS.add(GUI);
        ELEMENTS.add(FRAME);
        ELEMENTS.add(FORM);
        ELEMENTS.add(BUTTON);
        ELEMENTS.add(TEXT);
        ELEMENTS.add(IMAGE);
    }
    
    public static boolean isElement(String element) {
        return (ELEMENTS.contains(element));
    }
}
