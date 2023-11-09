package johnengine.basic.renderer.components;

public abstract class AVertexCollection {

    protected int handle;
    
    protected AVertexCollection() {
        this.handle = 0;
    }
    
    
    public abstract void generate();
    
    public abstract void bind();
    
    public abstract void unbind();
    
    public abstract void free();
    
    
    public int getHandle() {
        return this.handle;
    }
    
    public boolean wasGenerated() {
        return (this.handle != 0);
    }
}
