package johnengine.core.window.reqs;

import johnengine.core.window.Window;

public final class RFullscreen extends AWindowRequest {
    private final boolean isFullscreen;
    
    public RFullscreen(long windowID, boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    @Override
    protected void setState(WindowRequestContext context) {
        context.window.setFullscreen(this.isFullscreen);
    }

    @Override
    protected void setGLFW(WindowRequestContext context) {
        ((Window) context.window).DEBUGgoFullscreen();
    }
}
