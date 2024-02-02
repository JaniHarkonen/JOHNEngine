package johnengine.basic.opengl;

import org.lwjgl.system.MemoryUtil;

import johnengine.core.reqmngr.ARequest;
import johnengine.core.reqmngr.IRequestContext;

public abstract class AWindowRequestGL extends ARequest {

    @Override
    public void process(IRequestContext context) {
        WindowRequestContextGL winContext = (WindowRequestContextGL) context;
        this.setState(winContext);
        
        if( winContext.window.getWindowID() != MemoryUtil.NULL )
        this.setGLFW(winContext);
    }
    
    protected abstract void setState(WindowRequestContextGL context);
    
    protected abstract void setGLFW(WindowRequestContextGL context);
}
