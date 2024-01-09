package johnengine.basic.opengl.launcher;

import johnengine.basic.opengl.rewrite.WindowGL;
import johnengine.core.ALauncherFramework;

public class Window3DLauncherGL extends ALauncherFramework {
    
    protected WindowGL window;
    
    public Window3DLauncherGL() {
        super();
        this.window.start();
    }

    
    @Override
    protected void configureEngine() {
        this.window = WindowGL.setup3D();
        this.setupComponent(this.window);
    }
}
