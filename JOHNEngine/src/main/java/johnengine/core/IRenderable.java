package johnengine.core;

import johnengine.core.renderer.IRenderStrategy;

public interface IRenderable {
    public void render(IRenderStrategy renderStrategy);
}
