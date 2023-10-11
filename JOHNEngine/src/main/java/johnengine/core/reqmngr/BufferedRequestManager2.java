/*package johnengine.core.reqmngr;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BufferedRequestManager2 {
    
    //private List<ARequest> requestBuffer;
    //private ProcessorState processorState;
    private IRequestContext context;
    
    //private final Queue<ARequest> requestQueue;
    private final Queue<RequestBuffer> requestQueue;
    private RequestBuffer nextBuffer;
    
    public BufferedRequestManager2(IRequestContext context) {
        //this.requestBuffer = null;
        this.context = context;
        //this.requestQueue = new ConcurrentLinkedQueue<ARequest>();
        this.nextBuffer = new RequestBuffer();
        this.requestQueue = new ConcurrentLinkedQueue<RequestBuffer>();
        this.requestQueue.add(this.nextBuffer);
        
        //this.ready();
    }
    
    public BufferedRequestManager2() {
        this(null);
    }
    
    public void processRequests() {
        RequestBuffer buffer;
        while( (buffer = this.requestQueue.peek()) != null )
        {
            if( buffer.isReady() )
            this.requestQueue.poll().processRequests(this.context);
        }
        //int s = this.requestQueue.size();
        //for( int i = 0; i < s; i++ )
        //this.requestQueue.poll().process(this.context);
        /*if( !this.isProcessing() )
        return;
        
        for( ARequest request : this.requestBuffer )
        request.process(this.context);
        
        this.ready();
    }
    
    public void request(ARequest request) {
        //if( this.isAccepting() )
        //this.requestBuffer.add(request);
        //this.requestQueue.add(request);
        this.nextBuffer.add(request);
    }
    
    public void newBuffer() {
        if( this.nextBuffer.size() <= 0 )
        return;
        
        this.nextBuffer.ready();
        this.requestQueue.add(this.nextBuffer);
        this.nextBuffer = new RequestBuffer();
    }
    
    /*public void requestsStart() {
        if( this.isReady() )
        {
            this.requestBuffer = new ArrayList<ARequest>();
            this.accept();
        }
    }
    
    public void requestsEnd() {
        if( this.isAccepting() )
        this.processing();
    }
    
    private void accept() {
        this.processorState = ProcessorState.ACCEPTING;
    }
    
    private boolean isAccepting() {
        return (this.processorState == ProcessorState.ACCEPTING);
    }
    
    private void processing() {
        this.processorState = ProcessorState.PROCESSING;
    }
    
    private boolean isProcessing() {
        return (this.processorState == ProcessorState.PROCESSING);
    }
    
    private void ready() {
        this.processorState = ProcessorState.READY;
    }
    
    private boolean isReady() {
        return (this.processorState == ProcessorState.READY);
    }
    
    public void setContext(IRequestContext context) {
        this.context = context;
    }
}*/
