package johnengine.testing;

import johnengine.core.ALauncherFramework;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.networker.Networker;
import johnengine.core.window.Window;

public final class Launcher extends ALauncherFramework {

    @Override
    protected void configureEngine() {
        this.setupComponent(Window.setup());
        this.setupComponent(AssetManager.setup());
        this.setupComponent(Networker.setup());
        this.setupGame(new TestGame());
    }

    
}
