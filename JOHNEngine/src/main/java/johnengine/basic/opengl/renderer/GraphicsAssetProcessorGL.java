package johnengine.basic.opengl.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.opengl.renderer.asset.ARendererAsset;
import johnengine.basic.opengl.renderer.asset.Mesh;
import johnengine.basic.opengl.renderer.asset.MeshGL;
import johnengine.basic.opengl.renderer.asset.Texture;
import johnengine.basic.opengl.renderer.asset.TextureGL;
import johnengine.core.assetmngr.asset.IDeloadProcessor;
import johnengine.core.assetmngr.asset.ILoaderMonitor;

public class GraphicsAssetProcessorGL implements 
    ILoaderMonitor<IRendererAsset>,
    IDeloadProcessor<IRendererAsset>
{
    private final ConcurrentLinkedQueue<IRendererAsset> assetGenerationQueue;
    private final ConcurrentLinkedQueue<IRendererAsset> assetDisposalQueue;
    private final Map<Class<? extends IRendererAsset>, IGraphicsAsset<?>> graphicsAssetMap;
    
    public GraphicsAssetProcessorGL() {
        this.assetGenerationQueue = new ConcurrentLinkedQueue<>();
        this.assetDisposalQueue = new ConcurrentLinkedQueue<>();
        
        this.graphicsAssetMap = new HashMap<>();
        this.addGraphicsAsset(Mesh.class, (IGraphicsAsset<?>) (new MeshGL()));
        this.addGraphicsAsset(Texture.class, (IGraphicsAsset<?>) (new TextureGL()));
    }
    
    
    private void addGraphicsAsset(
        Class<? extends IRendererAsset> renderAssetClass, 
        IGraphicsAsset<?> graphicsAsset
    ) 
    {
        this.graphicsAssetMap.put(renderAssetClass, graphicsAsset);
    }
    
    
    @Override
    public void deloadAsset(IRendererAsset asset) {
        this.assetDisposalQueue.add(asset);
    }

    @Override
    public void processAssetDeloads() {
        IRendererAsset asset;
        while( (asset = this.assetDisposalQueue.poll()) != null )
        asset.getGraphics().dispose();
    }

    @Override
    public void assetLoaded(IRendererAsset asset) {
        this.assetGenerationQueue.add(asset);
    }

    @Override
    public void processLoadedAssets() {
        IRendererAsset asset;
        while( (asset = this.assetGenerationQueue.poll()) != null )
        {
            IGraphicsAsset<?> graphicsAsset = this.graphicsAssetMap.get(asset.getClass());
            graphicsAsset.createInstance(asset).generate();
            
            ((ARendererAsset<?, ?>) asset).setDeloader(this);
        }
    }
}
