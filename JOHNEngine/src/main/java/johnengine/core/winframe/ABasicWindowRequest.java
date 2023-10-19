package johnengine.core.winframe;

import org.lwjgl.system.MemoryUtil;

import johnengine.core.reqmngr.ARequest;
import johnengine.core.reqmngr.IRequestContext;

public abstract class ABasicWindowRequest extends ARequest {

    @Override
    public void process(IRequestContext context) {
        BasicWindowRequestContext winContext = (BasicWindowRequestContext) context;
        this.setState(winContext);
        
        if( winContext.window.getWindowID() != MemoryUtil.NULL )
        this.setGLFW(winContext);
    }
    
    protected abstract void setState(BasicWindowRequestContext context);
    
    protected abstract void setGLFW(BasicWindowRequestContext context);
}
