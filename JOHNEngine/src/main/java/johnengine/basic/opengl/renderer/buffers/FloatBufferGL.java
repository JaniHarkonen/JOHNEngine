package johnengine.basic.opengl.renderer.buffers;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

public class FloatBufferGL implements IBufferGL<Float> {
    
    private FloatBuffer buffer;

    public FloatBufferGL() {
        this.buffer = null;
    }
    
    
    @Override
    public void allocate(int length) {
        this.buffer = MemoryUtil.memAllocFloat(length);
    }
    
    @Override
    public boolean put(Float object) {
        this.buffer.put(object);
        return true;
    }

    @Override
    public void initialize(int target) {
        GL46.glBufferData(target, this.buffer, GL46.GL_STATIC_DRAW);
    }

    @Override
    public void free() {
        MemoryUtil.memFree(this.buffer);
    }
    
    @Override
    public void flip() {
        this.buffer.flip();
    }
}
