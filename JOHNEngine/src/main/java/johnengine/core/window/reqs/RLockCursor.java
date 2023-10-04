package johnengine.core.window.reqs;

public final class RLockCursor extends AWindowRequest {
    private final boolean isCursorLockedToCenter;
    
    public RLockCursor(long windowID, boolean isCursorLockedToCenter) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
    }

    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setCursorLockedToCenter(this.isCursorLockedToCenter);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        // TODO Auto-generated method stub
        
    }
}
