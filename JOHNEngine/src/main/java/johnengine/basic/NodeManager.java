package johnengine.basic;

import java.util.ArrayList;
import java.util.List;

public class NodeManager<P, C> {

    private P parent;
    private List<C> children;
    
    public NodeManager(P parent) {
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    
    public NodeManager() {
        this(null);
    }

    
    public NodeManager<P, C> addChild(C child) {
        this.children.add(child);
        return this;
    }
    
    public boolean removeChild(C child) {
        int s = this.children.size();
        for( int i = 0; i < s; i++ )
        {
            if( this.children.get(i) != child )
            continue;
            
            this.children.remove(i);
            return true;
        }
        
        return false;
    }
    
    public C removeChild(int index) {
        return this.children.remove(index);
    }
    
    public boolean childExists(C child) {
        for( C candidate : this.children )
        {
            if( candidate == child )
            return true;
        }
        
        return false;
    }
    
    
    public void setParent(P parent) {
        this.parent = parent;
    }
    
    public void removeParent() {
        this.parent = null;
    }
    
    
    public P getParent() {
        return this.parent;
    }
    
    public boolean isRoot() {
        return (this.parent == null);
    }
    
    public List<C> getChildren() {
        return this.children;
    }
    
    public C getChild(int index) {
        return this.children.get(index);
    }
    
    public int getChildCount() {
        return this.children.size();
    }
}
