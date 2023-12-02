package johnengine.core.renderer;

import johnengine.core.IRenderable;

public abstract class ARenderBufferStrategoid<I extends IRenderable, ST extends ARenderBufferStrategy> {
    
    protected ST strategy;
    
    protected ARenderBufferStrategoid(ST strategy) {
        this.strategy = strategy;
    }

    
    public abstract void execute(I instance);
}
