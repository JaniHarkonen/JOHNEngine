package johnengine.basic.window;

import johnengine.core.ALauncherFramework;

public class Window3DLauncher extends ALauncherFramework {
    
    protected Window window;
    
    public Window3DLauncher() {
        super();
        this.window.start();
    }

    
    @Override
    protected void configureEngine() {
        this.window = Window.setup3D();
        this.setupComponent(this.window);
    }
}
