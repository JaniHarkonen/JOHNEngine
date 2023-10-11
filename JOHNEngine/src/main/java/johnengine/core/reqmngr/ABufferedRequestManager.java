package johnengine.core.reqmngr;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ABufferedRequestManager {
    
    protected final Queue<RequestBuffer> requestQueue;
    protected IRequestContext context;
    protected RequestBuffer nextBuffer;
    
    public ABufferedRequestManager(IRequestContext context) {
        this.requestQueue = new ConcurrentLinkedQueue<RequestBuffer>();
        this.context = context;
        this.nextBuffer = new RequestBuffer();
        this.requestQueue.add(this.nextBuffer);
    }
    
    public ABufferedRequestManager() {
        this(null);
    }
    
    public abstract void processRequests();
    
    public void request(ARequest request) {
        this.nextBuffer.add(request);
    }
    
    public void newBuffer() {
        if( this.nextBuffer.size() <= 0 )
        return;
        
        this.nextBuffer.ready();
        this.requestQueue.add(this.nextBuffer);
        this.nextBuffer = new RequestBuffer();
    }
    
    public void setContext(IRequestContext context) {
        this.context = context;
    }
}
