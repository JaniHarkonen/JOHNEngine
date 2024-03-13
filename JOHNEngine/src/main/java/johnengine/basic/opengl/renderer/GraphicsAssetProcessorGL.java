package johnengine.basic.opengl.renderer;

import java.util.concurrent.ConcurrentLinkedQueue;

import johnengine.basic.opengl.renderer.asset.AGraphicsStrategyGL;

public class GraphicsAssetProcessorGL {
    private final ConcurrentLinkedQueue<AGraphicsStrategyGL<?>> graphicsGenerationQueue;
    private final ConcurrentLinkedQueue<AGraphicsStrategyGL<?>> graphicsDisposalQueue;
    
    public GraphicsAssetProcessorGL() {
        this.graphicsGenerationQueue = new ConcurrentLinkedQueue<>();
        this.graphicsDisposalQueue = new ConcurrentLinkedQueue<>();
    }
    
    
    public void disposeGraphics(AGraphicsStrategyGL<?> graphicsStrategy) {
        this.graphicsDisposalQueue.add(graphicsStrategy);
    }
    
    public void generateGraphics(AGraphicsStrategyGL<?> graphicsStrategy) {
        this.graphicsGenerationQueue.add(graphicsStrategy);
    }
    
    public void processGraphicsRequests() {
        AGraphicsStrategyGL<?> graphicsStrategy;
        while( (graphicsStrategy = this.graphicsGenerationQueue.poll()) != null )
        graphicsStrategy.generate();
        
        while( (graphicsStrategy = this.graphicsDisposalQueue.poll()) != null )
        graphicsStrategy.dispose();
    }
}
