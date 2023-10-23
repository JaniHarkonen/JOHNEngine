package johnengine.testing;

import johnengine.basic.window.Window3DLauncher;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.networker.Networker;

public final class Launcher extends Window3DLauncher {

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
