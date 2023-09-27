package testing;

import core.AbstractGame;
import core.assetmngr.AssetManager;
import core.engine.IEngineComponent;
import core.networker.Networker;
import core.window.Window;

public class TestGame extends AbstractGame {

    private Window gameWindow;
    private AssetManager assetManager;
    private Networker networker;

    @Override
    public void onCreate(IEngineComponent[] engineComponents) {
        this.gameWindow = (Window) engineComponents[0];
        this.assetManager = (AssetManager) engineComponents[1];
        this.networker = (Networker) engineComponents[2];
    }

    @Override
    public void tick(float deltaTime) {

    }

    @Override
    public void onClose() {

    }

}
