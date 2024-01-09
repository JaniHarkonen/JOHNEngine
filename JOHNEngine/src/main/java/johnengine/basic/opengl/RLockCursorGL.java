package johnengine.basic.opengl;

public final class RLockCursorGL extends AWindowRequestGL {
    private final boolean isCursorLockedToCenter;
    
    public RLockCursorGL(boolean isCursorLockedToCenter) {
        this.isCursorLockedToCenter = isCursorLockedToCenter;
    }

    
    @Override
    protected void setState(WindowRequestContextGL context) {
        context.window.setCursorLockedToCenter(this.isCursorLockedToCenter);
    }

    @Override
    protected void setGLFW(WindowRequestContextGL context) { }
}
