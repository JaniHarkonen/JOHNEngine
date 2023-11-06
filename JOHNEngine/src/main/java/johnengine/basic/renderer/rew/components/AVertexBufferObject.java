package johnengine.basic.renderer.rew.components;

import org.lwjgl.opengl.GL30;

public abstract class AVertexBufferObject<T> extends AVertexCollection {

    protected int attributeIndex;
    protected final int size;
    protected T data;
    
    protected AVertexBufferObject(int size) {
        this.attributeIndex = 0;
        this.size = size;
        this.data = null;
    }
    
    
    @Override
    public void free() {
        GL30.glDeleteBuffers(this.handle);
    }
    
    @Override
    public void bind() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.handle);
    }
    
    @Override
    public void unbind() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }
    
    
    void setIndex(int attributeIndex) {
        this.attributeIndex = attributeIndex;
    }
    
    void setData(T data) {
        this.data = data;
    }
}
