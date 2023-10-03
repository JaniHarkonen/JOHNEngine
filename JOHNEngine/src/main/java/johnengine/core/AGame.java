package johnengine.core;

import johnengine.core.engine.Engine;

public abstract class AGame implements IHasTick {

    public void onStart(Engine engine, IEngineComponent[] engineComponents) {}

    public abstract void onClose();
}
