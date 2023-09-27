package core.networker;

import core.engine.IEngineComponent;

public final class Networker implements IEngineComponent {

    public Networker() {

    }

    public static Networker setup() {
        return new Networker();
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
