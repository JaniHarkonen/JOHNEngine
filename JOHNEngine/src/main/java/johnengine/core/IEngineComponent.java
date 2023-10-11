package johnengine.core;

public interface IEngineComponent {

    public void beforeTick(float deltaTime);

    public void afterTick(float deltaTime);
}
