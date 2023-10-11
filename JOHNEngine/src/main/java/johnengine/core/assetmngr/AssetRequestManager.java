package johnengine.core.assetmngr;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.assetmngr.reqs.AAssetRequest;
import johnengine.core.reqmngr.ABufferedRequestManager;
import johnengine.core.reqmngr.ARequest;
import johnengine.core.reqmngr.IRequestContext;
import johnengine.core.reqmngr.RequestBuffer;
import johnengine.testing.DebugUtils;

public class AssetRequestManager extends ABufferedRequestManager {
    
    protected class AssetRequestSegment {
        private final List<AAssetRequest> requests;
        private int nextIndex;
        
        public AssetRequestSegment() {
            this.requests = new ArrayList<AAssetRequest>();
            this.nextIndex = 0;
        }
        
        public AAssetRequest nextRequest() {
            if( this.nextIndex < this.requests.size() )
            return this.requests.get(this.nextIndex++);
            
            return null;
        }
        
        public void add(AAssetRequest request) {
            this.requests.add(request);
        }
    }
    
    protected class AssetProcessor extends Thread {
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
    
    
    /*************************** CLASS STARTS ******************************/
    
    protected final int numberOfThreads;
    
    protected AssetRequestManager(int numberOfThreads, IRequestContext context) {
        this.numberOfThreads = numberOfThreads;
        this.context = context;
    }
    

    @Override
    public void request(ARequest request) {
        AAssetRequest arequest = ((AAssetRequest) request);
        if( !arequest.canQueue() )
        return;
        
        super.request(request);
        arequest.queueAsset();
    }
    
    @Override
    public void processRequests() {
        RequestBuffer buffer = this.requestQueue.poll();
        
        if( buffer == null)
        return;
        
        DebugUtils.log(this, buffer.size());
        
            // Create processor threads
        int processorCount = Math.min(this.numberOfThreads, buffer.size());
        AssetProcessor[] processors = new AssetProcessor[processorCount];
        for( int i = 0; i < processorCount; i++ )
        processors[i] = new AssetProcessor(this.context);
            
            // Distribute requets across the processor threads
        for( int i = 0; i < buffer.size(); i++ )
        processors[i % processorCount].addRequest((AAssetRequest) buffer.get(i));
        
            // Start the threads
        for( AssetProcessor processor : processors )
        processor.start();
    }
}
