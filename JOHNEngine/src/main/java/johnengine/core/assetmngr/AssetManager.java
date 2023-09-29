package johnengine.core.assetmngr;

import johnengine.core.IEngineComponent;

public final class AssetManager implements IEngineComponent {

    public AssetManager() {

    }

    public static AssetManager setup() {
        return new AssetManager();
    }

    @Override
    public int beforeTick(float deltaTime) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int afterTick(float deltaTime) {
        // TODO Auto-generated method stub
        return 0;
    }

}
