package johnengine.basic.renderer.components;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public abstract class AVBO<T> {
    protected int handle;
    protected int target;
    protected int size;
    protected int attributeIndex;
    
    protected AVBO(int target, int size, int attributeIndex) {
        this.target = target;
        this.size = size;
        this.attributeIndex = attributeIndex;
    }
    
    public abstract void generate(T data);
    
    public void bind() {
        GL30.glBindBuffer(this.target, this.handle);
    }
    
    public void unbind() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }
    
    public void delete() {
        GL30.glDeleteBuffers(this.handle);
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
