package johnengine.core.window.reqs;

public final class RBorder extends AWindowRequest {
    private final boolean hasBorder;
    
    public RBorder(long windowID, boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setBorder(this.hasBorder);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        // TODO Auto-generated method stub
        
    }
}
