package johnengine.basic.game;

import johnengine.core.IRenderable;

public interface ISceneGraphParent extends IRenderable {

    public default void attach(ISceneGraphChild child) {
        child.attached(this);
    }
    
    public default void detach(ISceneGraphChild child) {
        child.detached();
    }
}
