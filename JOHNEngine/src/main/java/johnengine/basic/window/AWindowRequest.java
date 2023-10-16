package johnengine.basic.window;

import johnengine.core.winframe.ABasicWindowRequest;
import johnengine.core.winframe.BasicWindowRequestContext;

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
