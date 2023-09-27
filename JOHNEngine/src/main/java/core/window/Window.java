package core.window;

import core.engine.IEngineComponent;

public final class Window implements IEngineComponent {

    public Window() {

    }

    public static Window setup() {
        return new Window();
    }

    public int beforeTick(float deltaTime) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int afterTick(float deltaTime) {
        // TODO Auto-generated method stub
        return 0;
    }
}
