package johnengine.core.reqmngr;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestManager {

    public static final int MAX_REQUEST_COUNT = 50;
    
    private Queue<ARequest> requestQueue;
    private int requestLimit;
    private IRequestContext context;
    
    public RequestManager(IRequestContext context) {
        this.requestQueue = new ConcurrentLinkedQueue<ARequest>();
        this.requestLimit = MAX_REQUEST_COUNT;
        this.context = context;
    }
    
    public RequestManager() {
        this(null);
    }
    
    public void processRequests() {
        if( this.requestQueue.size() <= 0 )
        return;
        
        ARequest request;
        for( int i = 0; i < this.requestLimit && (request = this.requestQueue.poll()) != null; i++ )
        request.process(this.context);
    }
    
    public void request(ARequest request) {
        while( this.requestQueue.size() >= this.requestLimit )
        this.requestQueue.poll();
        
        this.requestQueue.add(request);
    }
    
    public void setContext(IRequestContext context) {
        this.context = context;
    }
}
