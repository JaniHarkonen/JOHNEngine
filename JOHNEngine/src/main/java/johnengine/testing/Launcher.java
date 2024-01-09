package johnengine.testing;

import johnengine.basic.opengl.launcher.Window3DLauncherGL;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.networker.Networker;

public final class Launcher extends Window3DLauncherGL {

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
