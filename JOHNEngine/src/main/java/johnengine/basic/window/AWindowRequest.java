package johnengine.basic.window;

import johnengine.core.window.framework.ABasicWindowRequest;
import johnengine.core.window.framework.BasicWindowRequestContext;

public abstract class AWindowRequest extends ABasicWindowRequest {
    
    @Override
    protected void setState(BasicWindowRequestContext context) {
        this.setState((WindowRequestContext) context);
    }
    
    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        this.setGLFW((WindowRequestContext) context);
    }
    
    protected abstract void setState(WindowRequestContext context);
    
    protected abstract void setGLFW(WindowRequestContext context);
}
