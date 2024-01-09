package johnengine.core;

import johnengine.basic.opengl.rewrite.WindowGL;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;

public abstract class AGame {
    
    protected static AGame game;
    
    protected WindowGL window;
    protected AssetManager assetManager;
    protected Engine engine;

    public WindowGL getWindow() {
        return this.window;
    }
    
    public AssetManager getAssetManager() {
        return this.assetManager;
    }
    
    public Engine getEngine() {
        return this.engine;
    }
    
    
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {}
    
    public abstract void tick(float deltaTime);

    public abstract void onClose();
}
