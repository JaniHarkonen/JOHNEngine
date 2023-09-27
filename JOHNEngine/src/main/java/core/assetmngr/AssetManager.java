package core.assetmngr;

import core.engine.IEngineComponent;

public final class AssetManager implements IEngineComponent {
	
	public AssetManager() {
		
	}
	
	public static AssetManager setup() {
		return new AssetManager();
	}

	public int beforeTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int afterTick() {
		// TODO Auto-generated method stub
		return 0;
	}

}
