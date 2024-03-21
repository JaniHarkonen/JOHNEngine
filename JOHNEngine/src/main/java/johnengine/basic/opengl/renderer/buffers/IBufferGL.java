package johnengine.basic.opengl.renderer.buffers;

public interface IBufferGL<T> {

    public void allocate(int length);
    
    public boolean put(T object);
    
    public void initialize(int target);
    
    public void free();
    
    public void flip();
}
