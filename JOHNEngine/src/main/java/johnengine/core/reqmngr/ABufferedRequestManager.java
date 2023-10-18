package johnengine.core.reqmngr;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ABufferedRequestManager {
    
    protected final Queue<RequestBuffer> requestQueue;
    protected IRequestContext context;
    protected RequestBuffer nextBuffer;
    
    protected ABufferedRequestManager(IRequestContext context) {
        this.requestQueue = new ConcurrentLinkedQueue<>();
        this.context = context;
        this.nextBuffer = new RequestBuffer();
    }
    
    
    protected ABufferedRequestManager() {
        this(null);
    }
    
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
    
    public abstract void processRequests();
    
    
    /************************* SETTERS *************************/
    
    public void setContext(IRequestContext context) {
        this.context = context;
    }
}
