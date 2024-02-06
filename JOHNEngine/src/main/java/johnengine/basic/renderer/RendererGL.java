package johnengine.basic.renderer;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import johnengine.basic.opengl.WindowGL;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.strvaochc.CachedVAORenderStrategy;
import johnengine.core.renderer.IRenderStrategy;
import johnengine.core.renderer.IRenderer;
import johnengine.core.winframe.AWindowFramework;

public class RendererGL implements IRenderer {
    private WindowGL hostWindow;
    //private JWorld activeWorld;
    //private IRenderStrategy renderStrategy;
    private Map<String, IRenderStrategy> renderingPasses;
    private IRenderBufferStrategy renderBufferStrategy;
    private GraphicsAssetProcessorGL graphicsAssetProcessor;
    
    public RendererGL(AWindowFramework hostWindow, IRenderStrategy renderStrategy) {
        this.hostWindow = (WindowGL) hostWindow;
        //this.activeWorld = null;
        //this.renderStrategy = renderStrategy;
        this.renderingPasses = new HashMap<>();
        this.renderBufferStrategy = new DefaultRenderBufferStrategy();
        this.graphicsAssetProcessor = new GraphicsAssetProcessorGL();
    }
    
    public RendererGL(AWindowFramework hostWindow) {
        this(hostWindow, null);
        //this.renderStrategy = new CachedVAORenderStrategy(this);
        this.renderingPasses.put("scene-renderer", new CachedVAORenderStrategy(this));
    }
    
    
    @Override
    public void generateDefaults() {
        
            // Generate default assets that use OpenGL
        MeshGL.generateDefault();
        TextureGL.generateDefault();
    }

    @Override
    public void initialize() {
        GL.createCapabilities();
        this.generateDefaults();
        
        for( Map.Entry<String, IRenderStrategy> en : this.renderingPasses.entrySet() )
        en.getValue().prepare();
    }
    
    @Override
    public void generateRenderBuffer() {
        //this.renderBufferStrategy.execute(this.activeWorld, this.renderStrategy);
        for( Map.Entry<String, IRenderStrategy> en : this.renderingPasses.entrySet() )
        {
            IRenderStrategy renderStrategy = en.getValue();
            this.renderBufferStrategy.execute(renderStrategy.getRenderContext(), renderStrategy);
        }
    }
    
    @Override
    public void render() {
        this.graphicsAssetProcessor.processLoadedAssets();
        this.graphicsAssetProcessor.processAssetDeloads();
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        
        for( Map.Entry<String, IRenderStrategy> en : this.renderingPasses.entrySet() )
        en.getValue().render();
        //this.renderStrategy.render();
    }
    
    public void addRenderingPass(String passKey, IRenderStrategy passStrategy) {
        this.renderingPasses.put(passKey, passStrategy);
    }
    
    
    /*
    public void setActiveWorld(JWorld world) {
        if( world != null )
        this.activeWorld = world;
    }
    
    public JWorld getActiveWorld() {
        return this.activeWorld;
    }
    */
    
    /*public IRenderStrategy getRenderStrategy() {
        return this.renderStrategy;
    }*/
    
    public IRenderStrategy getStrategyOfRenderingPass(String passKey) {
        return this.renderingPasses.get(passKey);
    }
    
    public GraphicsAssetProcessorGL getGraphicsAssetProcessor() {
        return this.graphicsAssetProcessor;
    }

    @Override
    public AWindowFramework getWindow() {
        return this.hostWindow;
    }
}
