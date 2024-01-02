package johnengine.basic.game;

import johnengine.core.IRenderable;

public interface ISceneGraphChild extends IRenderable {

    public void attached(ISceneGraphParent parent);
    public void detached();
}
