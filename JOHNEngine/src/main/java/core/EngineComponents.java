package core;

import core.assetmngr.AssetManager;
import core.networker.Networker;
import core.renderer.Renderer;

public class EngineComponents {

	public final AssetManager assetManager;
	public final Networker networker;
	public final Renderer renderer;
	
	public EngineComponents(AssetManager assetManager, Networker networker, Renderer renderer) {
		this.assetManager = assetManager;
		this.networker = networker;
		this.renderer = renderer;
	}
}
