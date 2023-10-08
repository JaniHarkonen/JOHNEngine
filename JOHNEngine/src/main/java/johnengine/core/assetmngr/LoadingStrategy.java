package johnengine.core.assetmngr;

import java.util.List;

import johnengine.core.reqmngr.ARequest;

public abstract class LoadingStrategy {
    
    public abstract List<Thread> spawnThreads(List<ARequest> requestBuffer);
    
    public void run(AssetManager assetManager, List<ARequest> requestBuffer) {
        List<Thread> threads = this.spawnThreads(requestBuffer);
        
        for( Thread t : threads )
        t.start();
    }
}
