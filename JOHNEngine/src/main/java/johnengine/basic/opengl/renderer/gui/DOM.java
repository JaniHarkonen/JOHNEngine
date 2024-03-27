package johnengine.basic.opengl.renderer.gui;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.renderer.IRenderBuffer;

public class DOM implements IRenderBuffer<DOM> {

    /********************* Node-class *********************/
    
    class Node {
        AGUISubmission<?> submission;
        Node parent;
        List<Node> children;
        
        private Node(AGUISubmission<?> submission, Node parent) {
            this.submission = submission;
            this.parent = parent;
            this.children = new ArrayList<>();
        }
        
        
        private void addChild(Node child) {
            this.children.add(child);
        }
    }
    
    
    /********************* Node-class *********************/
    
    private List<Node> frameNodes;
    private Node currentNode;
    
    public DOM() {
        this.frameNodes = new ArrayList<>();
        this.currentNode = null;
    }
    
    
    @Override
    public DOM createInstance() {
        return new DOM();
    }
    
    public void addFrame(SubmitFrame frameSubmission) {
        this.currentNode = new Node(frameSubmission, this.currentNode);
        this.frameNodes.add(this.currentNode);
    }
    
    public void addNode(AGUISubmission<?> submission) {
        Node node = new Node(submission, this.currentNode);
        this.currentNode.addChild(node);
        this.currentNode = node;
    }
    
    public void traverseUp() {
        this.currentNode = this.currentNode.parent;
    }
    
    public List<Node> getFrameNodes() {
        return this.frameNodes;
    }
}
