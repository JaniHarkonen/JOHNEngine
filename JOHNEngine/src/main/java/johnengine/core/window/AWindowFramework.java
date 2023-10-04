package johnengine.core.window;

import org.lwjgl.system.MemoryUtil;

import johnengine.core.reqmngr.RequestManager;
import johnengine.core.window.reqs.RBorder;
import johnengine.core.window.reqs.RChangeCursorVisibility;
import johnengine.core.window.reqs.RChangeTitle;
import johnengine.core.window.reqs.RFullscreen;
import johnengine.core.window.reqs.RLockCursor;
import johnengine.core.window.reqs.RMaximize;
import johnengine.core.window.reqs.RMove;
import johnengine.core.window.reqs.RResize;
import johnengine.core.window.reqs.RVSync;

public abstract class AWindowFramework {
    
    public enum STATE {
        INITIALIZING,
        CLOSED,
        OPEN
    }

    public static class Properties {
        public static final int DEFAULT_WIDTH = 640;
        public static final int DEFAULT_HEIGHT = 480;
        public static final int DEFAULT_X = 0;
        public static final int DEFAULT_Y = 0;
        public static final String DEFAULT_TITLE = "Powered by JOHNEngine v.1.0.0";
        
        public static final boolean DEFAULT_IS_FULLSCREEN = false;
        public static final boolean DEFAULT_HAS_BORDER = true;
        public static final boolean DEFAULT_LOCK_CURSOR_TO_CENTER = false;
        public static final boolean DEFAULT_IS_CURSOR_VISIBLE = true;
        public static final boolean DEFAULT_USE_VSYNC = false;
        
        private int width;
        private int height;
        private int x;
        private int y;
        private int monitorWidth;
        private int monitorHeight;
        private long fps;
        private String title;
        
        private boolean isFullscreen;
        private boolean hasBorder;
        private boolean lockCursorToCenter;
        private boolean isCursorVisible;
        private AWindowFramework.STATE windowState;
        private boolean useVSync;
        private boolean isFocused;
        private boolean isMaximized;
        
        public Properties() {
            this.width = Properties.DEFAULT_WIDTH;
            this.height = Properties.DEFAULT_HEIGHT;
            this.x = Properties.DEFAULT_X;
            this.y = Properties.DEFAULT_Y;
            this.monitorWidth = -1;
            this.monitorWidth = -1;
            this.fps = 0;
            this.title = Properties.DEFAULT_TITLE;
            
            this.isFullscreen = Properties.DEFAULT_IS_FULLSCREEN;
            this.hasBorder = Properties.DEFAULT_HAS_BORDER;
            this.lockCursorToCenter = Properties.DEFAULT_LOCK_CURSOR_TO_CENTER;
            this.isCursorVisible = Properties.DEFAULT_IS_CURSOR_VISIBLE;
            this.useVSync = Properties.DEFAULT_USE_VSYNC;
            this.isFocused = true;
            this.isMaximized = true;
            
            this.windowState = AWindowFramework.STATE.INITIALIZING;
        }
        
        public void copy(Properties source) {
            this.width = source.width;
            this.height = source.height;
            this.x = source.x;
            this.y = source.y;
            this.monitorWidth = source.monitorWidth;
            this.monitorWidth = source.monitorHeight;
            this.fps = source.fps;
            this.title = source.title;
            
            this.isFullscreen = source.isFullscreen;
            this.hasBorder = source.hasBorder;
            this.lockCursorToCenter = source.lockCursorToCenter;
            this.isCursorVisible = source.isCursorVisible;
            this.useVSync = source.useVSync;
            this.isFocused = source.isFocused;
            this.isMaximized = source.isMaximized;
            
            this.windowState = source.windowState;
        }
    }
    
    protected final Properties updatingProperties;
    protected final Properties snapshotProperties;
    protected final RequestManager requestManager;
    protected long windowID;
    protected long primaryMonitorID;
    
    protected AWindowFramework(
        Properties updatingProperties, 
        Properties snapshotProperties, 
        RequestManager requestManager
    ) {
        
        this.updatingProperties = updatingProperties;
        this.snapshotProperties = snapshotProperties;
        this.requestManager = requestManager;
        
        this.reset();
    }
    
    
    protected void reset() {
        this.windowID = MemoryUtil.NULL;
        this.primaryMonitorID = MemoryUtil.NULL;
    }
    
    /************************* REQUESTS ***************************/
    
    public void move(int x, int y) {
        this.requestManager.request(new RMove(this.windowID, x, y));
    }
    
    public void resize(int width, int height) {
        this.requestManager.request(new RResize(this.windowID, width, height));
    }
    
    public void changeTitle(String title) {
        this.requestManager.request(new RChangeTitle(this.windowID, title));
    }
    
    public void enterFullscreen() {
        this.requestManager.request(new RFullscreen(this.windowID, true));
    }
    
    public void exitFullscreen() {
        this.requestManager.request(new RFullscreen(this.windowID, false));
    }
    
    public void showBorder() {
        this.requestManager.request(new RBorder(this.windowID, true));
    }
    
    public void hideBorder() {
        this.requestManager.request(new RBorder(this.windowID, false));
    }

    public void lockCursorToCenter() {
        this.requestManager.request(new RLockCursor(this.windowID, true));
    }
    
    public void freeCursorLock() {
        this.requestManager.request(new RLockCursor(this.windowID, false));
    }
    
    public void showCursor() {
        this.requestManager.request(new RChangeCursorVisibility(this.windowID, true));
    }
    
    public void hideCursor() {
        this.requestManager.request(new RChangeCursorVisibility(this.windowID, false));
    }

    public void enableVSync() {
        this.requestManager.request(new RVSync(this.windowID, true));
    }
    
    public void disableVSync() {
        this.requestManager.request(new RVSync(this.windowID, false));
    }

    public void maximize() {
        this.requestManager.request(new RMaximize(this.windowID, true));
    }
    
    public void maximizeRestore() {
        this.requestManager.request(new RMaximize(this.windowID, false));
    }
    
    
    /************************** SETTERS ***************************/
    
    
    
    public void setPosition(int x, int y) {
        this.updatingProperties.x = x;
        this.updatingProperties.y = y;
    }
    
    public void setSize(int width, int height) {
        this.updatingProperties.width = width;
        this.updatingProperties.height = height;
    }

    public void setMonitorSize(int width, int height) {
        this.updatingProperties.monitorWidth = width;
    }
    
    public void setFPS(long fps) {
        this.updatingProperties.fps = fps;
    }
    
    public void setTitle(String title) {
        this.updatingProperties.title = title;
    }
    
    public void setFullscreen(boolean isFullscreen) {
        this.updatingProperties.isFullscreen = isFullscreen;
    }
    
    public void setBorder(boolean hasBorder) {
        this.updatingProperties.hasBorder = hasBorder;
    }

    public void setCursorLockedToCenter(boolean isLockedToCenter) {
        this.updatingProperties.lockCursorToCenter = isLockedToCenter;
    }

    public void setCursorVisibility(boolean isVisible) {
        this.updatingProperties.isCursorVisible = isVisible;
    }

    public void setVSync(boolean useVSync) {
        this.updatingProperties.useVSync = useVSync;
    }

    public void setFocused(boolean isFocused) {
        this.updatingProperties.isFocused = isFocused;
    }
    
    public void setMaximized(boolean isMaximized) {
        this.updatingProperties.isMaximized = isMaximized;
    }
    
    public void setWindowState(AWindowFramework.STATE state) {
        this.updatingProperties.windowState = state;
    }
    
    
    /************************** GETTERS ***************************/
    
    
    
    public int getX() {
        return this.snapshotProperties.x;
    }
    
    public int getY() {
        return this.snapshotProperties.y;
    }
    
    public int getWidth() {
        return this.snapshotProperties.width;
    }
    
    public int getHeight() {
        return this.snapshotProperties.height;
    }
    
    public int getMonitorWidth() {
        return this.snapshotProperties.monitorWidth;
    }
    
    public int getMonitorHeight() {
        return this.snapshotProperties.monitorHeight;
    }
    
    public long getFPS() {
        return this.snapshotProperties.fps;
    }
    
    public String getTitle() {
        return this.snapshotProperties.title;
    }
    
    public boolean isFullscreen() {
        return this.snapshotProperties.isFullscreen;
    }

    public boolean hasBorder() {
        return this.snapshotProperties.hasBorder;
    }

    public boolean isCursorLockedToCenter() {
        return this.snapshotProperties.lockCursorToCenter;
    }

    public boolean isCursorVisible() {
        return this.snapshotProperties.isCursorVisible;
    }

    public boolean useVSync() {
        return this.snapshotProperties.useVSync;
    }

    public boolean isFocused() {
        return this.snapshotProperties.isFocused;
    }

    public boolean isMaximized() {
        return this.snapshotProperties.isMaximized;
    }
    
    public boolean hasWindowClosed() {
        return (this.snapshotProperties.windowState == AWindowFramework.STATE.CLOSED);
    }
    
    public long getWindowID() {
        return this.windowID;
    }
}
