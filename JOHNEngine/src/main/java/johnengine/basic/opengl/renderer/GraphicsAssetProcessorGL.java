package johnengine.basic.opengl.renderer;

import java.util.concurrent.ConcurrentLinkedQueue;

import johnengine.basic.opengl.renderer.asset.IGraphicsStrategyGL;

public class GraphicsAssetProcessorGL {
    private final ConcurrentLinkedQueue<IGraphicsStrategyGL> graphicsGenerationQueue;
    private final ConcurrentLinkedQueue<IGraphicsStrategyGL> graphicsDisposalQueue;
    
    public GraphicsAssetProcessorGL() {
        this.graphicsGenerationQueue = new ConcurrentLinkedQueue<>();
        this.graphicsDisposalQueue = new ConcurrentLinkedQueue<>();
    }
    
    
    public void disposeGraphics(IGraphicsStrategyGL graphicsStrategy) {
        this.graphicsDisposalQueue.add(graphicsStrategy);
    }
    
    public void generateGraphics(IGraphicsStrategyGL graphicsStrategy) {
        this.graphicsGenerationQueue.add(graphicsStrategy);
    }
    
    public void processGraphicsRequests() {
        IGraphicsStrategyGL graphicsStrategy;
        while( (graphicsStrategy = this.graphicsGenerationQueue.poll()) != null )
        graphicsStrategy.generate();
        
        while( (graphicsStrategy = this.graphicsDisposalQueue.poll()) != null )
        graphicsStrategy.dispose();
    }
}
