package johnengine.core.window.reqs;

import johnengine.core.reqmngr.IRequestContext;
import johnengine.core.window.AWindowFramework;

public class WindowRequestContext implements IRequestContext {

    public AWindowFramework window;
    
    public WindowRequestContext(AWindowFramework window) {
        this.window = window;
    }
}
