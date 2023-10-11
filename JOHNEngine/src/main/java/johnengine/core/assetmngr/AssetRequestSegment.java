package johnengine.core.assetmngr;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.assetmngr.reqs.AAssetRequest;

public class AssetRequestSegment {
    public final List<AAssetRequest> requests;
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
