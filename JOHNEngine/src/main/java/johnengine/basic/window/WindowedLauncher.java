package johnengine.basic.window;

import johnengine.core.ALauncherFramework;

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
