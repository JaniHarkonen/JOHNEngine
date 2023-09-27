package core;

public abstract class AbstractGame {
	
	protected final EngineComponents components;
	
	protected AbstractGame(EngineComponents components) {
		this.components = components;
	}
	
	public abstract void onCreate(EngineComponents components);
	public abstract void tick(float deltaTime);
	public abstract void onClose();
	
	public EngineComponents getComponents() {
		return this.components;
	}
}
