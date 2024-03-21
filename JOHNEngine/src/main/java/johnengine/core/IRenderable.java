package johnengine.core;

import johnengine.core.renderer.IRenderPass;

public interface IRenderable {
    public void submit(IRenderPass renderPass);
}
