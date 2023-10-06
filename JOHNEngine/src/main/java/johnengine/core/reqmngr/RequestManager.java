package johnengine.core.reqmngr;

import java.util.ArrayList;
import java.util.List;

public class RequestManager {
    
    /**
     * State of the processor that handles the requests. Depending on the 
     * state the RequestManager is either accepting requests or ignoring
     * them.
     * 
     * @author User
     *
     */
    public enum ProcessorState {
        
        /**
         * The processor is idling and new requests are being accepted into
         * the buffer.
         */
        ACCEPTING,
        
        /**
         * The processor is handling the accepted requests and no requests 
         * are being accepted.
         */
        PROCESSING,
        
        /**
         * The processor is ready to accept new requests, however, the 
         * buffer is either null or full and requestsStart() must be called
         * to reset the buffer (typically called before a game tick).
         */
        READY
    }
    
    private List<ARequest> requestBuffer;
    private ProcessorState processorState;
    private IRequestContext context;
    
    public RequestManager(IRequestContext context) {
        this.requestBuffer = null;
        this.context = context;
        
        this.ready();
    }
    
    public RequestManager() {
        this(null);
    }
    
    public void processRequests() {
        if( !this.isProcessing() )
        return;
        
        for( ARequest request : this.requestBuffer )
        request.process(this.context);
        
        this.ready();
    }
    
    public void request(ARequest request) {
        if( this.isAccepting() )
        this.requestBuffer.add(request);
    }
    
    public void requestsStart() {
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
}
