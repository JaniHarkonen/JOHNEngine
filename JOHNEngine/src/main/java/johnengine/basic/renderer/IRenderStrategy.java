package johnengine.basic.renderer;

import johnengine.basic.game.JWorld;
import johnengine.core.IRenderBufferStrategy;

public interface IRenderStrategy {

    public void execute(JWorld world, IRenderBufferStrategy renderBufferStrategy);
}
