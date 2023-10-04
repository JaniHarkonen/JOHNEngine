package johnengine.core.assetmngr;

import johnengine.core.IEngineComponent;

public final class AssetManager implements IEngineComponent {

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

}
