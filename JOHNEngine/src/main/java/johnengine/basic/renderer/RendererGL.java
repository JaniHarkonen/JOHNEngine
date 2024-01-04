package johnengine.basic.renderer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.strvaochc.CachedVAORenderStrategy;
import johnengine.basic.window.Window;
import johnengine.core.renderer.IRenderStrategy;
import johnengine.core.renderer.IRenderer;
import johnengine.core.winframe.AWindowFramework;

public class RendererGL implements IRenderer {
    private Window hostWindow;
    private JWorld activeWorld;
    private IRenderStrategy renderStrategy;
    private IRenderBufferStrategy renderBufferStrategy;
    private GraphicsAssetProcessorGL graphicsAssetProcessor;
    
    public RendererGL(AWindowFramework hostWindow, IRenderStrategy renderStrategy) {
        this.hostWindow = (Window) hostWindow;
        this.activeWorld = null;
        this.renderStrategy = renderStrategy;
        this.renderBufferStrategy = new DefaultRenderBufferStrategy();
        this.graphicsAssetProcessor = new GraphicsAssetProcessorGL();
    }
    
    public RendererGL(AWindowFramework hostWindow) {
        this(hostWindow, null);
        this.renderStrategy = new CachedVAORenderStrategy(this);
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
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.generateDefaults();
        this.renderStrategy.prepare();
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL13.glEnable(GL13.GL_MULTISAMPLE);
        //GL11.glCullFace(GL11.GL_BACK);
    }
    
    @Override
    public void generateRenderBuffer() {
        this.renderBufferStrategy.execute(this.activeWorld, this.renderStrategy);
    }
    
    @Override
    public void render() {
        this.graphicsAssetProcessor.processLoadedAssets();
        this.graphicsAssetProcessor.processAssetDeloads();
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        this.renderStrategy.render();
    }
    
    
    public void setActiveWorld(JWorld world) {
        if( world != null )
        this.activeWorld = world;
    }
    
    public JWorld getActiveWorld() {
        return this.activeWorld;
    }
    
    public IRenderStrategy getRenderStrategy() {
        return this.renderStrategy;
    }
    
    public GraphicsAssetProcessorGL getGraphicsAssetProcessor() {
        return this.graphicsAssetProcessor;
    }

    @Override
    public AWindowFramework getWindow() {
        return this.hostWindow;
    }
}
