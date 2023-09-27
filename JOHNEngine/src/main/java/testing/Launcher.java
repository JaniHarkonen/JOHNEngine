package testing;

import core.LauncherFramework;
import core.assetmngr.AssetManager;
import core.networker.Networker;
import core.window.Window;

public final class Launcher extends LauncherFramework {

    @Override
    protected void configureEngine() {
        this.setupComponent(Window.setup());
        this.setupComponent(AssetManager.setup());
        this.setupComponent(Networker.setup());
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
