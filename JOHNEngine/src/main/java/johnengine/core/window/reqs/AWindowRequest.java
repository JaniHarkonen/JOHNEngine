package johnengine.core.window.reqs;

import johnengine.core.reqmngr.ARequest;
import johnengine.core.reqmngr.IRequestContext;

public abstract class AWindowRequest extends ARequest {

    @Override
    public void process(IRequestContext context) {
        WindowRequestContext winContext = (WindowRequestContext) context;
        this.setState(winContext);
        this.setGLFW(winContext);
    }
    
    protected abstract void setState(WindowRequestContext context);
    
    protected abstract void setGLFW(WindowRequestContext context);
}
