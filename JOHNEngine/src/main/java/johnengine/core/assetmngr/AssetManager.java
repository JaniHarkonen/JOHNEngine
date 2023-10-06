package johnengine.core.assetmngr;

import johnengine.core.IEngineComponent;
import johnengine.core.threadable.AThreadable;

public final class AssetManager extends AThreadable implements IEngineComponent {

    public AssetManager() {
        
    }

    public static AssetManager setup() {
        return new AssetManager();
    }

    @Override
    public void beforeTick(float deltaTime) {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterTick(float deltaTime) {
        // TODO Auto-generated method stub
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }
}
