package johnengine.core.window;

import johnengine.core.window.framework.BasicWindowRequestContext;

public class WindowRequestContext extends BasicWindowRequestContext {

    public Window window;
    
    public WindowRequestContext(Window window) {
        super(window);
        this.window = window;
    }
}
