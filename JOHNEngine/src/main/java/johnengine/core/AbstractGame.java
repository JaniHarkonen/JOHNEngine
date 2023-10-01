package johnengine.core;

import johnengine.core.engine.Engine;

public abstract class AbstractGame implements IHasTick {

    public abstract void onStart(Engine engine, IEngineComponent[] engineComponents);

    public abstract void onClose();
}
