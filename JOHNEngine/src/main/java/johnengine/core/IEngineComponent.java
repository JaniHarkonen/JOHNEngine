package johnengine.core;

public interface IEngineComponent {

    public int beforeTick(float deltaTime);

    public int afterTick(float deltaTime);
}
