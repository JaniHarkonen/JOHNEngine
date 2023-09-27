package core;

import core.engine.IEngineComponent;

public abstract class AbstractGame {

    public abstract void onCreate(IEngineComponent[] engineComponents);

    public abstract void tick(float deltaTime);

    public abstract void onClose();
}
