package johnengine.core.renderer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import johnengine.basic.renderer.asset.ARendererAsset;
import johnengine.core.winframe.AWindowFramework;

public abstract class ARenderer {
    
    protected final Queue<ARendererAsset.Data> generationQueue;
    protected final Queue<ARendererAsset.Data> disposalQueue;
    protected final ARenderBufferStrategy renderBufferStrategy;
    protected AWindowFramework hostWindow;
    
    protected ARenderer(
        AWindowFramework hostWindow, 
        Queue<ARendererAsset.Data> generationQueue,
        Queue<ARendererAsset.Data> disposalQueue,
        ARenderBufferStrategy renderBufferStrategy
    ) {
        this.hostWindow = hostWindow;
        this.generationQueue = generationQueue;
        this.disposalQueue = disposalQueue;
        this.renderBufferStrategy = renderBufferStrategy;
    }
    
    protected ARenderer(
        AWindowFramework hostWindow, 
        ARenderBufferStrategy renderBufferStrategy
    ) {
        this(
            hostWindow, 
            new ConcurrentLinkedQueue<ARendererAsset.Data>(), 
            new ConcurrentLinkedQueue<ARendererAsset.Data>(),
            renderBufferStrategy
        );
    }

    public abstract void generateDefaults();
    
    public abstract void initialize();
    
    public abstract void generateRenderBuffer();
    
    public abstract void render();
    
    
    public void generateAsset(ARendererAsset.Data asset) {
        this.generationQueue.add(asset);
    }
    
    public void disposeAssetData(ARendererAsset.Data data) {
        this.disposalQueue.add(data);
    }
    
    public void processAssetRequests() {
        ARendererAsset.Data assetData;
        while( (assetData = this.generationQueue.poll()) != null )
        assetData.generate();
        
        while( (assetData = this.disposalQueue.poll()) != null )
        assetData.dispose();
    }
    
    
    public AWindowFramework getWindow() {
        return this.hostWindow;
    }
    
    public <T extends IDrawable> ARenderBufferStrategoid<T, ? extends ARenderBufferStrategy> getRenderBufferStrategoid(T instance) {
        return this.renderBufferStrategy.getStrategoid(instance);
    }
}
