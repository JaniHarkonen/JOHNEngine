package johnengine.basic.assets.animation;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

public class NodeTEMP {

    public final String name;
    public final NodeTEMP parent;
    public final Matrix4f transform;
    
    public final List<NodeTEMP> children;
    
    public NodeTEMP(String name, NodeTEMP parent, Matrix4f transform) {
        this.name = name;
        this.parent = parent;
        this.transform = transform;
        this.children = new ArrayList<>();
    }
    
    
    public void addChild(NodeTEMP child) {
        this.children.add(child);
    }
}
