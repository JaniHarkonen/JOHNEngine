package johnengine.basic.window;

import johnengine.core.winframe.BasicWindowRequestContext;

public class WindowRequestContext extends BasicWindowRequestContext {

    public Window window;   // "Overrides" super.window to avoid casting
    
    public WindowRequestContext(Window window) {
        super(window);
        this.window = window;
    }
}
