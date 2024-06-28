package johnengine.basic.game;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.IRenderable;

public interface ISceneGraphNode extends IRenderable {

    public void attached(ISceneGraphParent parent);
    public void detached();
    
    public default List<ISceneGraphNode> getChildren() {
        return new ArrayList<>();
    }
}
