package johnengine.basic.opengl;

import johnengine.core.reqmngr.IRequestContext;

public class WindowRequestContextGL implements IRequestContext {

    public WindowGL window;
    
    public WindowRequestContextGL(WindowGL window) {
        this.window = window;
    }
}
