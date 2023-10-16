package johnengine.testing;

import johnengine.basic.window.WindowedLauncher;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.networker.Networker;

public final class Launcher extends WindowedLauncher {

    @Override
    protected void configureEngine() {
        super.configureEngine();
        this.setupComponent(AssetManager.setup());
        this.setupComponent(Networker.setup());
        this.setupGame(new TestGame());
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
