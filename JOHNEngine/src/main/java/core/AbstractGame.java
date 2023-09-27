package core;

import core.engine.Engine;
import core.engine.IEngineComponent;

public abstract class AbstractGame {

    public abstract void onStart(Engine engine, IEngineComponent[] engineComponents);

    public abstract void tick(float deltaTime);

    public abstract void onClose();
}
