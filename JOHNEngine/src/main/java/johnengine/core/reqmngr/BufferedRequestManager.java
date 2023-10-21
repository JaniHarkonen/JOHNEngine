package johnengine.core.reqmngr;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BufferedRequestManager extends ABufferedRequestManager {
    
    public BufferedRequestManager(IRequestContext context) {
        super(context, new ConcurrentLinkedQueue<>());
    }
    
    public BufferedRequestManager() {
        this(null);
    }

    @Override
    public void processRequests() {
        RequestBuffer buffer;
        
        while( (buffer = this.requestQueue.peek()) != null )
        {
            if( buffer.isReady() )
            this.requestQueue.poll().processRequests(this.context);
        }
    }
}
