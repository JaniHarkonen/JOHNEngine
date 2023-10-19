package johnengine.core.winframe;

import johnengine.core.reqmngr.IRequestContext;

public class BasicWindowRequestContext implements IRequestContext {

    public AWindowFramework window;
    
    public BasicWindowRequestContext(AWindowFramework window) {
        this.window = window;
    }
}
