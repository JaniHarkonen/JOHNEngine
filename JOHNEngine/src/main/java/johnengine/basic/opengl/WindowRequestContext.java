package johnengine.basic.opengl;

import johnengine.core.winframe.BasicWindowRequestContext;

public class WindowRequestContext extends BasicWindowRequestContext {

    public WindowGL window;   // "Overrides" super.window to avoid casting
    
    public WindowRequestContext(WindowGL window) {
        super(window);
        this.window = window;
    }
}
