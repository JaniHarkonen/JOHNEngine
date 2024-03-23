package johnengine.basic.opengl.renderer;

import org.lwjgl.opengl.GL11;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.opengl.WindowGL;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.cachedvao.CachedVAORenderPass;
import johnengine.basic.opengl.renderer.gui.GUIRenderPass;
import johnengine.core.FileUtils;
import johnengine.core.renderer.IRenderBufferPopulator;
import johnengine.core.renderer.IRenderPass;
import johnengine.core.renderer.IRenderer;
import johnengine.core.renderer.RenderPassManager;
import johnengine.testing.DebugUtils;

public class RendererGL implements IRenderer {
    private WindowGL hostWindow;
    private RenderPassManager renderPassManager;
    private IRenderBufferPopulator renderBufferPopulator;
    private GraphicsAssetProcessorGL graphicsAssetProcessor;
    private String resourceRootFolder;
    
    public RendererGL(WindowGL hostWindow) {
        this.hostWindow = (WindowGL) hostWindow;
        this.renderPassManager = new RenderPassManager();
        this.renderBufferPopulator = new DefaultRenderBufferPopulator();
        this.graphicsAssetProcessor = new GraphicsAssetProcessorGL();
        this.resourceRootFolder = "";
        
        this.renderPassManager.addRenderPass(
            "scene-renderer", new CachedVAORenderPass(this)
        );
        this.renderPassManager.addRenderPass(
            "gui-renderer", new GUIRenderPass(this)
        );
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
        
        for( String passKey : this.renderPassManager.getOrder() )
        this.renderPassManager.getPass(passKey).prepare();
    }
    
    @Override
    public void generateRenderBuffer() {
        for( String passKey : this.renderPassManager.getOrder() )
        {
            IRenderPass renderPass = this.renderPassManager.getPass(passKey);
            this.renderBufferPopulator.execute(renderPass);
        }
    }
    
    @Override
    public void render() {
        this.graphicsAssetProcessor.processGraphicsRequests();
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        
        for( String passKey : this.renderPassManager.getOrder() )
        this.renderPassManager.getPass(passKey).render();
    }
    
    
    public void setResourceRootFolder(String resourceRootFolder) {
        this.resourceRootFolder = FileUtils.normalizePathSlashes(resourceRootFolder) + "/";
    }
    
    public GraphicsAssetProcessorGL getGraphicsAssetProcessor() {
        return this.graphicsAssetProcessor;
    }

    @Override
    public WindowGL getWindow() {
        return this.hostWindow;
    }
    
    @Override
    public RenderPassManager getRenderPassManager() {
        return this.renderPassManager;
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
