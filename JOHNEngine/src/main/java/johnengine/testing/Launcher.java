package johnengine.testing;

import johnengine.basic.opengl.WindowGL;
import johnengine.core.ALauncherFramework;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.logger.ConsoleRecorder;
import johnengine.core.logger.Logger;
import johnengine.core.networker.Networker;

public final class Launcher extends ALauncherFramework {
    
    private WindowGL window;
    
    public Launcher() {
        super();
        this.window = null;
    }
    

    @Override
    protected void configureEngine() {
        this.window = WindowGL.setup3D();
        this.setupComponent(this.window);
        this.setupComponent(AssetManager.setup());
        this.setupComponent(Networker.setup());
        this.setupGame(new TestGame());
    }
    
    @Override
    public void launch() {
        super.launch();
        this.window.start();
    }

    
    public static void main(String[] args) {
        Logger.configure(
            Logger.SHOW_TIMESTAMP | 
            Logger.SHOW_SEVERITY | 
            Logger.SHOW_THREAD | 
            Logger.SHOW_CALLER
        );
        Logger.setVerbosity(Logger.VERBOSITY_VERBOSE);
        Logger.addRecorder("console", new ConsoleRecorder());
        
        Launcher launcher = new Launcher();
        launcher.launch();
    }
}
