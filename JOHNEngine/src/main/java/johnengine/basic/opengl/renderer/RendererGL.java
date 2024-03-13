package johnengine.basic.opengl.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.opengl.WindowGL;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.strvaochc.CachedVAORenderStrategy;
import johnengine.core.FileUtils;
import johnengine.core.renderer.IRenderStrategy;
import johnengine.core.renderer.IRenderer;
import johnengine.testing.DebugUtils;

public class RendererGL implements IRenderer {
    private WindowGL hostWindow;
    //private JWorld activeWorld;
    //private IRenderStrategy renderStrategy;
    private Map<String, IRenderStrategy> renderingPasses;
    private List<String> renderingPassOrder;
    private IRenderBufferStrategy renderBufferStrategy;
    private GraphicsAssetProcessorGL graphicsAssetProcessor;
    private String resourceRootFolder;
    
    public RendererGL(WindowGL hostWindow, IRenderStrategy renderStrategy) {
        this.hostWindow = (WindowGL) hostWindow;
        //this.activeWorld = null;
        //this.renderStrategy = renderStrategy;
        this.renderingPasses = new HashMap<>();
        this.renderingPassOrder = new ArrayList<>();
        this.renderBufferStrategy = new DefaultRenderBufferStrategy();
        this.graphicsAssetProcessor = new GraphicsAssetProcessorGL();
        this.resourceRootFolder = "";
    }
    
    public RendererGL(WindowGL hostWindow) {
        this(hostWindow, null);
        this.addRenderingPass("scene-renderer", new CachedVAORenderStrategy(this));
        //this.addRenderingPass("gui-renderer", new GUIRenderStrategy(this));
    }
    
    
    @Override
    public void generateDefaults() {
        
            // Generate default assets that use OpenGL
        MeshGraphicsGL.generateDefault(this);
        TextureGraphicsGL.generateDefault(this);
        DebugUtils.log(this, "defaults generated");
    }

    @Override
    public void initialize() {
        this.generateDefaults();
        
        for( String passKey : this.renderingPassOrder )
        this.renderingPasses.get(passKey).prepare();
    }
    
    @Override
    public void generateRenderBuffer() {
        for( String passKey : this.renderingPassOrder )
        {
            IRenderStrategy renderStrategy = this.renderingPasses.get(passKey);
            this.renderBufferStrategy.execute(renderStrategy.getRenderContext(), renderStrategy);
        }
    }
    
    @Override
    public void render() {
        this.graphicsAssetProcessor.processGraphicsRequests();
        
        GL11.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        
        for( String passKey : this.renderingPassOrder )
        this.renderingPasses.get(passKey).render();
    }
    
    public void addRenderingPass(String passKey, IRenderStrategy passStrategy) {
        this.renderingPasses.put(passKey, passStrategy);
        this.renderingPassOrder.add(passKey);
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
    
    public void setResourceRootFolder(String resourceRootFolder) {
        this.resourceRootFolder = FileUtils.normalizePathSlashes(resourceRootFolder) + "/";
    }
    
    
    public IRenderStrategy getStrategyOfRenderingPass(String passKey) {
        return this.renderingPasses.get(passKey);
    }
    
    public GraphicsAssetProcessorGL getGraphicsAssetProcessor() {
        return this.graphicsAssetProcessor;
    }

    @Override
    public WindowGL getWindow() {
        return this.hostWindow;
    }
    
    public String getResourceRootFolder() {
        return this.resourceRootFolder;
    }
    
    public MeshGraphicsGL getGraphicsStrategy(Mesh mesh) {
        MeshGraphicsGL gl = new MeshGraphicsGL(this, mesh, false);
        return gl;
    }
    
    public TextureGraphicsGL getGraphicsStrategy(Texture texture) {
        return new TextureGraphicsGL(this, texture, false);
    }
}
