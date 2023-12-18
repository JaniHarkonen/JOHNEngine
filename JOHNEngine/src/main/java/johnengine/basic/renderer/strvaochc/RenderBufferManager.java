package johnengine.basic.renderer.strvaochc;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderBufferManager {

    private ConcurrentLinkedQueue<RenderBuffer> renderBufferQueue;
    private RenderBuffer lastBuffer;
    private RenderBuffer currentBuffer;
    
    public RenderBufferManager() {
        this.renderBufferQueue = new ConcurrentLinkedQueue<>();
        this.lastBuffer = null;
        this.currentBuffer = new RenderBuffer();
    }
    
    
    public void newBuffer() {
        this.renderBufferQueue.add(this.currentBuffer);
        this.currentBuffer = new RenderBuffer();
    }
    
    public void addRenderUnit(RenderUnit unit) {
        this.currentBuffer.addUnit(unit);
    }
    
    public RenderBuffer getLatestBuffer() {
        RenderBuffer renderBuffer = this.renderBufferQueue.poll();
        
        if( renderBuffer != null )
        this.lastBuffer = renderBuffer;

        return this.lastBuffer;
    }
}
