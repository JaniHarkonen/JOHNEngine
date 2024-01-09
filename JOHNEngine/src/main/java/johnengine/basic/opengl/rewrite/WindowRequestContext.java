package johnengine.basic.opengl.rewrite;

import johnengine.core.reqmngr.IRequestContext;

public class WindowRequestContext implements IRequestContext {

    public WindowGL window;
    
    public WindowRequestContext(WindowGL window) {
        this.window = window;
    }
}
