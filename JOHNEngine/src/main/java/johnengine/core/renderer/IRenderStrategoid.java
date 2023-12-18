package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderStrategoid<T extends IRenderable> {

    public void execute(T target);
}
