package core;

import java.util.ArrayList;
import java.util.List;

import core.engine.Engine;
import core.engine.IEngineComponent;

public abstract class LauncherFramework {
	
	private List<IEngineComponent> engineConfiguration;
	
	private AbstractGame game;
	
	public LauncherFramework() {
		this.engineConfiguration = new ArrayList<IEngineComponent>();
		this.configureEngine();
		Engine.STATE engineState = Engine.start(
			game,
			this.engineConfiguration.toArray(new IEngineComponent[this.engineConfiguration.size()])
		);
		
		if( engineState == Engine.STATE.START_FAILED )
		throw new RuntimeException("Failed to run the engine!");
		else if( engineState == Engine.STATE.START_FAILED_NO_GAME )
		throw new RuntimeException("Cannot run a null game!");
	}
	
	protected abstract void configureEngine();
	
	protected void setupComponent(IEngineComponent component) {
		this.engineConfiguration.add(component);
	}
	
	protected void setupGame(AbstractGame game) {
		this.game = game;
	}
}
