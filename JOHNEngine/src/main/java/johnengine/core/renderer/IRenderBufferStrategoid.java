package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderBufferStrategoid<T extends IRenderable> {

    public void execute(T target);
}
