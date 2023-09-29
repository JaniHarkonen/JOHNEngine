package core;

import core.engine.Engine;

public abstract class AbstractGame implements IHasTick {

    public abstract void onStart(Engine engine, IEngineComponent[] engineComponents);

    //@Override
    //public abstract int tick(float deltaTime);

    public abstract void onClose();
}
