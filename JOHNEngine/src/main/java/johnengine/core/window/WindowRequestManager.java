package johnengine.core.window;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WindowRequestManager {

    private class Buffer {
        private Map<String, IWindowRequest> requests;
        
        private Buffer() {
            this.requests = new HashMap<>();
        }
        
        private void put(IWindowRequest request) {
            this.requests.put(request.getPropertyKey(), request);
        }
        
        private void process(IWindow.Properties affectedProperties) {
            for( Map.Entry<String, IWindowRequest> en : this.requests.entrySet() )
            en.getValue().fulfill(affectedProperties);
        }
        
        private boolean isEmpty() {
            return this.requests.isEmpty();
        }
    }
    
    
    private Buffer currentBuffer;
    private Queue<Buffer> requestQueue;
    
    public WindowRequestManager() {
        this.currentBuffer = new Buffer();
        this.requestQueue = new ConcurrentLinkedQueue<>();
    }
    
    
    public void newBuffer() {
        if( this.currentBuffer.isEmpty() )
        return;
        
        this.requestQueue.add(this.currentBuffer);
        this.currentBuffer = new Buffer();
    }
    
    public void addRequest(IWindowRequest request) {
        this.currentBuffer.put(request);
    }
    
    public void processRequests(IWindow.Properties affectedProperties) {
        Buffer buffer;
        while( (buffer = this.requestQueue.poll()) != null )
        buffer.process(affectedProperties);
    }
}
