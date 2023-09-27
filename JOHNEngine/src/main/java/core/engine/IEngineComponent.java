package core.engine;

public interface IEngineComponent {

	public int beforeTick(float deltaTime);
	public int afterTick(float deltaTime);
}
