package johnengine.core.networker;

import johnengine.core.IEngineComponent;

public final class Networker implements IEngineComponent {

    public static Networker setup() {
        return new Networker();
    }
    
    
    public Networker() {

    }
    

    @Override
    public void beforeTick(float deltaTime) {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterTick(float deltaTime) {
        // TODO Auto-generated method stub
    }

}
