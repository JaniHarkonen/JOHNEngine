package johnengine.core.renderer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderBufferManager<T extends IRenderBuffer<T>> {

    private ConcurrentLinkedQueue<T> renderBufferQueue;
    private T lastBuffer;
    private T currentBuffer;
    
    public RenderBufferManager(T defaultRenderBuffer) {
        this.renderBufferQueue = new ConcurrentLinkedQueue<>();
        this.lastBuffer = defaultRenderBuffer.createInstance();
        this.currentBuffer = defaultRenderBuffer.createInstance();
    }
    
    
    public void newBuffer() {
        this.renderBufferQueue.add(this.currentBuffer);
        this.currentBuffer = this.currentBuffer.createInstance();
    }
    
    public T peekNext() {
        return this.renderBufferQueue.peek();
    }
    
    public T poll() {
        T renderBuffer = this.renderBufferQueue.poll();
        
        if( renderBuffer != null )
        this.lastBuffer = renderBuffer;

        return this.lastBuffer;
    }
    
    public T getCurrentBuffer() {
        return this.currentBuffer;
    }
}

