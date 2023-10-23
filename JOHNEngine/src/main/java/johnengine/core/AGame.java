package johnengine.core;

import johnengine.basic.window.Window;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;

public abstract class AGame implements IHasTick {
    
    protected static AGame game;
    
    protected Window window;
    protected AssetManager assetManager;
    protected Engine engine;

    public static Window getWindow() {
        return game.window;
    }
    
    public static AssetManager getAssetManager() {
        return game.assetManager;
    }
    
    public static Engine getEngine() {
        return game.engine;
    }
    
    
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {}

    public abstract void onClose();
}
