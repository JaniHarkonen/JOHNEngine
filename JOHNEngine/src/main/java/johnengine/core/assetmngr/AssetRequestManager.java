package johnengine.core.assetmngr;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.assetmngr.reqs.AAssetRequest;
import johnengine.core.reqmngr.ABufferedRequestManager;
import johnengine.core.reqmngr.ARequest;
import johnengine.core.reqmngr.IRequestContext;
import johnengine.core.reqmngr.RequestBuffer;

public class AssetRequestManager extends ABufferedRequestManager {
    
    protected class AssetRequestSegment {
        private final List<AAssetRequest> requests;
        private int nextIndex;
        
        public AssetRequestSegment() {
            this.requests = new ArrayList<>();
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
        private final AssetRequestManager manager;
        
        private AssetProcessor(IRequestContext context, AssetRequestManager manager) {
            this.requestSegment = new AssetRequestSegment();
            this.context = context;
            this.manager = manager;
        }
        
        public void addRequest(AAssetRequest request) {
            this.requestSegment.add(request);
        }
        
        @Override
        public void run() {
            AAssetRequest request;
            
            while( (request = this.requestSegment.nextRequest()) != null )
            request.process(this.context);
            
            this.manager.finishThread();
        }
    }
    
    
    /********************** AssetRequestManager-class ************************/
    
    protected final int numberOfThreads;
    protected int numberOfFinishedThreads;
    
    protected AssetRequestManager(int numberOfThreads, IRequestContext context) {
        this.numberOfThreads = numberOfThreads;
        this.context = context;
        this.numberOfFinishedThreads = this.numberOfThreads;
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
            // Only process requests once the previous RequestBuffer has been processed
        if( !this.hasFinished() )
        return;
        
        RequestBuffer buffer = this.requestQueue.poll();
        
        if( buffer == null || buffer.size() <= 0)
        return;
        
            // Create processor threads
        int processorCount = Math.min(this.numberOfThreads, buffer.size());
        AssetProcessor[] processors = new AssetProcessor[processorCount];
        this.numberOfFinishedThreads = this.numberOfThreads - processorCount;
        
        for( int i = 0; i < processorCount; i++ )
        processors[i] = new AssetProcessor(this.context, this);
            
            // Distribute requets across the processor threads
        for( int i = 0; i < buffer.size(); i++ )
        processors[i % processorCount].addRequest((AAssetRequest) buffer.get(i));
        
            // Start the processor threads
        for( AssetProcessor processor : processors )
        processor.start();
    }
    
    protected void finishThread() {
        this.numberOfFinishedThreads++;
    }
    
    
    /***************************** GETTERS *****************************/
    
    public boolean hasFinished() {
        return (this.numberOfFinishedThreads == this.numberOfThreads);
    }
}
