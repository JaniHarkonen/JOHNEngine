package johnengine.core.window.framework;

public final class RLockCursor extends ABasicWindowRequest {
    private final boolean isCursorLockedToCenter;
    
    public RLockCursor(long windowID, boolean isCursorLockedToCenter) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
    }

    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setCursorLockedToCenter(this.isCursorLockedToCenter);
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) {
        // TODO Auto-generated method stub
        
    }
}
