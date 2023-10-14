package johnengine.core.window.framework;

import org.lwjgl.system.MemoryUtil;

import johnengine.core.reqmngr.BufferedRequestManager;
import johnengine.core.window.RFullscreen;

public abstract class AWindowFramework {
    
    public enum STATE {
        INITIALIZING,
        CLOSED,
        OPEN
    }

    public static class Properties {
        public static final int DEFAULT_WIDTH = 640;
        public static final int DEFAULT_HEIGHT = 480;
        public static final int DEFAULT_X = 32;
        public static final int DEFAULT_Y = 32;
        public static final String DEFAULT_TITLE = "Powered by JOHNEngine v.1.0.0";
        
        public static final boolean DEFAULT_IS_FULLSCREEN = false;
        public static final boolean DEFAULT_HAS_BORDER = true;
        public static final boolean DEFAULT_LOCK_CURSOR_TO_CENTER = false;
        public static final boolean DEFAULT_IS_CURSOR_VISIBLE = true;
        public static final boolean DEFAULT_USE_VSYNC = false;
        
        public int width;
        public int height;
        public int x;
        public int y;
        public int monitorWidth;
        public int monitorHeight;
        public long fps;
        public String title;
        
        public boolean isFullscreen;
        public boolean hasBorder;
        public boolean lockCursorToCenter;
        public boolean isCursorVisible;
        public AWindowFramework.STATE windowState;
        public boolean useVSync;
        public boolean isFocused;
        public boolean isMaximized;
        
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
            this.isMaximized = false;
            
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
    protected final BufferedRequestManager requestManager;
    protected long windowID;
    protected long primaryMonitorID;
    
    protected AWindowFramework(
        Properties updatingProperties, 
        Properties snapshotProperties, 
        BufferedRequestManager requestManager
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
    
    /************************ LISTENERS ***************************/
    
    protected void focusListener(boolean isFocused) {
        this.setFocused(isFocused);
    }
    
    protected void maximizeListener(boolean isMaximized) {
        this.setMaximized(isMaximized);
    }
    
    protected void positionListener(int xpos, int ypos) {
        this.setPosition(xpos, ypos);
    }
    
    protected void resizeListener(int width, int height) {
        this.setSize(width, height);
    }
    
    protected void closeListener() {
        this.setWindowState(STATE.CLOSED);
    }
    
    
    /************************* REQUESTS ***************************/
    
    
    
    public AWindowFramework move(int x, int y) {
        this.requestManager.request(new RMove(this.windowID, x, y));
        return this;
    }
    
    public AWindowFramework resize(int width, int height) {
        this.requestManager.request(new RResize(this.windowID, width, height));
        return this;
    }
    
    public AWindowFramework changeTitle(String title) {
        this.requestManager.request(new RChangeTitle(this.windowID, title));
        return this;
    }
    
    public AWindowFramework enterFullscreen() {
        this.requestManager.request(new RFullscreen(this.windowID, true));
        return this;
    }
    
    public AWindowFramework exitFullscreen() {
        this.requestManager.request(new RFullscreen(this.windowID, false));
        return this;
    }

    public AWindowFramework lockCursorToCenter() {
        this.requestManager.request(new RLockCursor(this.windowID, true));
        return this;
    }
    
    public AWindowFramework freeCursorLock() {
        this.requestManager.request(new RLockCursor(this.windowID, false));
        return this;
    }
    
    public AWindowFramework showCursor() {
        this.requestManager.request(new RChangeCursorVisibility(this.windowID, true));
        return this;
    }
    
    public AWindowFramework hideCursor() {
        this.requestManager.request(new RChangeCursorVisibility(this.windowID, false));
        return this;
    }

    public AWindowFramework enableVSync() {
        this.requestManager.request(new RVSync(this.windowID, true));
        return this;
    }
    
    public AWindowFramework disableVSync() {
        this.requestManager.request(new RVSync(this.windowID, false));
        return this;
    }
    
    public AWindowFramework moveMouse(int x, int y) {
        this.requestManager.request(new RMoveMouse(this.windowID, x, y));
        return this;
    }
    
    /************************** SETTERS ***************************/
    
    
    
    protected void setPosition(int x, int y) {
        this.updatingProperties.x = x;
        this.updatingProperties.y = y;
    }
    
    protected void setSize(int width, int height) {
        this.updatingProperties.width = width;
        this.updatingProperties.height = height;
    }

    protected void setMonitorSize(int width, int height) {
        this.updatingProperties.monitorWidth = width;
    }
    
    protected void setFPS(long fps) {
        this.updatingProperties.fps = fps;
    }
    
    protected void setTitle(String title) {
        this.updatingProperties.title = title;
    }
    
    protected void setFullscreen(boolean isFullscreen) {
        this.updatingProperties.isFullscreen = isFullscreen;
    }
    
    protected void setBorder(boolean hasBorder) {
        this.updatingProperties.hasBorder = hasBorder;
    }

    protected void setCursorLockedToCenter(boolean isLockedToCenter) {
        this.updatingProperties.lockCursorToCenter = isLockedToCenter;
    }

    protected void setCursorVisibility(boolean isVisible) {
        this.updatingProperties.isCursorVisible = isVisible;
    }

    protected void setVSync(boolean useVSync) {
        this.updatingProperties.useVSync = useVSync;
    }

    protected void setFocused(boolean isFocused) {
        this.updatingProperties.isFocused = isFocused;
    }
    
    protected void setMaximized(boolean isMaximized) {
        this.updatingProperties.isMaximized = isMaximized;
    }
    
    protected void setWindowState(AWindowFramework.STATE state) {
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
