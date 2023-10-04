package johnengine.core.networker;

import johnengine.core.IEngineComponent;

public final class Networker implements IEngineComponent {

    public Networker() {

    }

    public static Networker setup() {
        return new Networker();
    }

    public void beforeTick(float deltaTime) {
        // TODO Auto-generated method stub
    }

    public void afterTick(float deltaTime) {
        // TODO Auto-generated method stub
    }

}
