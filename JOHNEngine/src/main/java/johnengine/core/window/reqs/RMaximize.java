package johnengine.core.window.reqs;

public final class RMaximize extends AWindowRequest {
    private final boolean isMaximized;
    
    public RMaximize(long windowID, boolean isMaximized) {
        this.isMaximized = isMaximized;
    }


    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setMaximized(this.isMaximized);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        
    }
}
