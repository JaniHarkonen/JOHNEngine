package johnengine.core;

import johnengine.core.window.Window;

public class WindowedLauncher extends ALauncherFramework {
    
    private Window window;
    
    public WindowedLauncher() {
        super();
        this.window.start();
    }

    @Override
    protected void configureEngine() {
        this.window = Window.setup();
        this.setupComponent(this.window);
    }
}
