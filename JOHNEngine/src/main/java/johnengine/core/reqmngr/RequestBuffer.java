package johnengine.core.reqmngr;

import java.util.ArrayList;
import java.util.List;

public class RequestBuffer {

    private final List<ARequest> requests;
    private boolean isReady;
    
    public RequestBuffer() {
        this.requests = new ArrayList<>();
        this.isReady = false;
    }
    
    
    public void add(ARequest request) {
        this.requests.add(request);
    }
    
    public void processRequests(IRequestContext context) {
        for( ARequest request : this.requests )
        request.process(context);
    }
    
    
    /************************** SETTERS ***************************/
    
    public void ready() {
        this.isReady = true;
    }
    
    
    /************************** GETTERS ***************************/
    
    public boolean isReady() {
        return this.isReady;
    }
    
    public int size() {
        return this.requests.size();
    }
    
    public ARequest get(int index) {
        return this.requests.get(index);
    }
}
