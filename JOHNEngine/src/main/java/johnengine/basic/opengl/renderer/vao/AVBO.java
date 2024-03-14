package johnengine.basic.opengl.renderer.vao;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;
import johnengine.basic.opengl.renderer.buffers.IBufferGL;

public abstract class AVBO<D, T> implements IGeneratable, IBindable {
    protected IBufferGL<T> buffer;
    protected int target;
    protected int size;
    protected int handle;
    protected D[] data;
    protected VBOType type;
    
    public AVBO(int target, int size, IBufferGL<T> buffer) {
        this.target = target;
        this.size = size;
        this.buffer = buffer;
        this.handle = -1;
        this.data = null;
        this.type = null;
    }

    
    public boolean generate(D[] data) {
        this.setData(data);
        return this.generate();
    }
    
    @Override
    public boolean generate() {
        this.handle = GL46.glGenBuffers();
        this.bind();
        this.buffer.allocate(this.data.length * this.size);
        this.populateBuffer();
        this.buffer.flip();
        this.buffer.initialize(this.target);
        this.buffer.free();
        this.unbind();
        
        return true;
    }
    
    protected abstract void populateBuffer();
    
    @Override
    public boolean dispose() {
        GL46.glDeleteBuffers(this.handle);
        return true;
    }
    
    @Override
    public boolean bind() {
        GL46.glBindBuffer(this.target, this.handle);
        return true;
    }
    
    @Override
    public boolean unbind() {
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
        return true;
    }
    
    
    public void setData(D[] data) {
        this.data = data;
    }
    
    public void setType(VBOType type) {
        this.type = type;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
    
    public int getTarget() {
        return this.target;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public VBOType getType() {
        return this.type;
    }
}
