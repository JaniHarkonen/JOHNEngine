package johnengine.basic.renderer.vertex;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;

public abstract class AVBO<S> implements IGeneratable, IBindable {
    protected int handle;
    protected int target;
    protected int size;
    protected S source;
    
    protected AVBO(int target, int size) {
        this.target = target;
        this.size = size;
        this.source = null;
    }
    
    
    public boolean generate(S source) {
        this.setSource(source);
        return this.generate();
    }
    
    @Override
    public boolean dispose() {
        GL30.glDeleteBuffers(this.handle);
        return true;
    }
    
    @Override
    public boolean bind() {
        GL30.glBindBuffer(this.target, this.handle);
        return true;
    }
    
    @Override
    public boolean unbind() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        return true;
    }
    
    
    public void setSource(S source) {
        this.source = source;
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
    
    
    protected FloatBuffer allocateFloatBuffer(int length) {
        return MemoryUtil.memAllocFloat(this.size * length);
    }
    
    protected IntBuffer allocateIntBuffer(int length) {
        return MemoryUtil.memAllocInt(this.size * length);
    }
    
    protected void freeAllocation(Buffer buffer) {
        MemoryUtil.memFree(buffer);
    }
    
    protected void genBuffers() {
        this.handle = GL30.glGenBuffers();
    }
}
