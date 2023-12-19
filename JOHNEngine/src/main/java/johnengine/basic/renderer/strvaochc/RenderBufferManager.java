package johnengine.basic.renderer.strvaochc;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderBufferManager {

    private ConcurrentLinkedQueue<RenderBuffer> renderBufferQueue;
    private RenderBuffer lastBuffer;
    private RenderBuffer currentBuffer;
    
    public RenderBufferManager() {
        this.renderBufferQueue = new ConcurrentLinkedQueue<>();
        this.lastBuffer = new RenderBuffer();
        this.currentBuffer = new RenderBuffer();
    }
    
    
    public void newBuffer() {
        this.renderBufferQueue.add(this.currentBuffer);
        this.currentBuffer = new RenderBuffer();    
    }
    
    public RenderBuffer peekNext() {
        return this.renderBufferQueue.peek();
    }
    
    public RenderBuffer poll() {
        RenderBuffer renderBuffer = this.renderBufferQueue.poll();
        
        if( renderBuffer != null )
        this.lastBuffer = renderBuffer;

        return this.lastBuffer;
    }
    
    RenderBuffer getCurrentBuffer() {
        return this.currentBuffer;
    }
}
