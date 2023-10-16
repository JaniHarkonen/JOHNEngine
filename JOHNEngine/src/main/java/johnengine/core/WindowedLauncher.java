package johnengine.core;

import johnengine.basic.window.Window;

public class WindowedLauncher extends ALauncherFramework {
    
    protected Window window;
    
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
