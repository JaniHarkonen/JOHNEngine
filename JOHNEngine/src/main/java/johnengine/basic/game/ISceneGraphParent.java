package johnengine.basic.game;

public interface ISceneGraphParent extends ISceneGraphNode {

    public default void attach(ISceneGraphNode child) {
        child.attached(this);
    }
    
    public default void detach(ISceneGraphNode child) {
        child.detached();
    }
}
