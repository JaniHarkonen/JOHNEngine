package johnengine.game;

import johnengine.core.ALauncherFramework;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.networker.Networker;
import johnengine.core.window.Window;
import johnengine.testing.TestGame;

public class WindowedLauncher extends ALauncherFramework {
    
    public WindowedLauncher() {
        super();
        Window.instance.start();
    }

    @Override
    protected void configureEngine() {
        this.setupComponent(Window.setup());
        this.setupComponent(AssetManager.setup());
        this.setupComponent(Networker.setup());
        this.setupGame(new TestGame());
    }
    
    public static void main(String[] args) {
        new WindowedLauncher();
    }
}
