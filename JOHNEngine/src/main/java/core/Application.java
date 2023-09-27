package core;

public class Application {
	
	public Application() {
		Engine.STATE engineState = Engine.start(Engine.SETUP_RENDERER | Engine.SETUP_ASSET_MANAGER | Engine.SETUP_NETWORKER);
		
		if( engineState == Engine.STATE.START_FAILED )
		throw new RuntimeException("Failed to run the engine!");
	}

	public static void main(String[] args) {
		new Application();
	}
}
