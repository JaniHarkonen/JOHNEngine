package testing;

import core.AbstractGame;
import core.assetmngr.AssetManager;
import core.engine.Engine;
import core.engine.IEngineComponent;
import core.networker.Networker;
import core.window.Window;

public class TestGame extends AbstractGame {

    private Window gameWindow;
    private AssetManager assetManager;
    private Networker networker;
    private Engine engine;

    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine         = engine;
        this.gameWindow     = (Window)          engineComponents[0];
        this.assetManager   = (AssetManager)    engineComponents[1];
        this.networker      = (Networker)       engineComponents[2];
        
        this.engine.setTickRate(60);
        this.gameWindow.setTitle("ezzzpzzz B)");
        this.gameWindow.resize(1280, 720);
        this.gameWindow.launch();
    }

    @Override
    public void tick(float deltaTime) {
        if( this.gameWindow.hasWindowClosed() )
        this.engine.stop();
    }

    @Override
    public void onClose() {
        DebugUtils.log(this, "BYEEEE");
    }

}
