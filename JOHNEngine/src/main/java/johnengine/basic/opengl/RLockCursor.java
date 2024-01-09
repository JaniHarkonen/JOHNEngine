package johnengine.basic.opengl;

public final class RLockCursor extends AWindowRequest {
    private final boolean isCursorLockedToCenter;
    
    public RLockCursor(boolean isCursorLockedToCenter) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
    }

    
    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setCursorLockedToCenter(this.isCursorLockedToCenter);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) { }
}
