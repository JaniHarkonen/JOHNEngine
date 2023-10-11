package johnengine.core.assetmngr;

import johnengine.core.assetmngr.reqs.AAssetRequest;
import johnengine.core.reqmngr.ABufferedRequestManager;
import johnengine.core.reqmngr.IRequestContext;
import johnengine.core.reqmngr.RequestBuffer;

public class AssetRequestManager extends ABufferedRequestManager {
    
    private class AssetProcessor extends Thread {
        private final AssetRequestSegment requestSegment;
        private final IRequestContext context;
        
        private AssetProcessor(IRequestContext context) {
            this.requestSegment = new AssetRequestSegment();
            this.context = context;
        }
        
        public void addRequest(AAssetRequest request) {
            this.requestSegment.add(request);
        }
        
        @Override
        public void start() {
            AAssetRequest request;
            while( (request = this.requestSegment.nextRequest()) != null )
            request.process(this.context);
        }
    }
    
    protected final int numberOfThreads;
    
    protected AssetRequestManager(int numberOfThreads, IRequestContext context) {
        this.numberOfThreads = numberOfThreads;
        this.context = context;
    }

    @Override
    public void processRequests() {
        RequestBuffer buffer = this.requestQueue.poll();
        
        if( buffer == null )
        return;
        
            // Create processor threads
        int processorCount = Math.min(this.numberOfThreads, buffer.size());
        AssetProcessor[] processors = new AssetProcessor[processorCount];
        for( int i = 0; i < processorCount; i++ )
        processors[i] = new AssetProcessor(this.context);
            
            // Distribute requets across the processor threads
        for( int i = 0; i < buffer.size(); i++ )
        processors[i % this.numberOfThreads].addRequest((AAssetRequest) buffer.get(i));
        
            // Start the threads
        for( AssetProcessor processor : processors )
        processor.start();
    }
}
