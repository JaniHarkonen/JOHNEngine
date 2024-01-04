package johnengine.basic.renderer;

import johnengine.basic.game.JWorld;
import johnengine.core.renderer.IRenderStrategy;

public interface IRenderBufferStrategy {

    public void execute(JWorld world, IRenderStrategy renderStrategy);
}
