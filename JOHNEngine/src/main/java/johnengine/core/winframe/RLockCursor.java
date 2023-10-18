package johnengine.core.winframe;

public final class RLockCursor extends ABasicWindowRequest {
    private final boolean isCursorLockedToCenter;
    
    public RLockCursor(boolean isCursorLockedToCenter) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
    }

    
    @Override
    protected void setState(BasicWindowRequestContext context) {
        context.window.setCursorLockedToCenter(this.isCursorLockedToCenter);
    }

    @Override
    protected void setGLFW(BasicWindowRequestContext context) { }
}
