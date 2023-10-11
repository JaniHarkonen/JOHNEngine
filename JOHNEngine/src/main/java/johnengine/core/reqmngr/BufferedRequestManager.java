package johnengine.core.reqmngr;

public class BufferedRequestManager extends ABufferedRequestManager {

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
