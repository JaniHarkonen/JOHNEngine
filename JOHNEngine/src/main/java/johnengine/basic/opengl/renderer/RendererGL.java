package johnengine.basic.opengl.renderer;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.opengl.WindowGL;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.cachedvao.CachedVAORenderPass;
import johnengine.basic.opengl.renderer.gui.GUIRenderPass;
import johnengine.core.FileUtils;
import johnengine.core.logger.Logger;
import johnengine.core.renderer.IRenderPass;
import johnengine.core.renderer.IRenderer;
import johnengine.core.renderer.RenderPassManager;

public class RendererGL implements IRenderer {
    private WindowGL hostWindow;
    private RenderPassManager renderPassManager;
    private GraphicsAssetProcessorGL graphicsAssetProcessor;
    private String resourceRootFolder;
    
    public RendererGL(WindowGL hostWindow) {
        this.hostWindow = (WindowGL) hostWindow;
        this.renderPassManager = new RenderPassManager();
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
        long currentTime = System.nanoTime();
        Logger.log(
            Logger.VERBOSITY_VERBOSE, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            "[OpenGL] Generating default mesh graphics strategy..."
        );
        MeshGraphicsGL.generateDefault(this);
        Logger.log(
            Logger.VERBOSITY_STANDARD, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            "Generated default mesh graphics strategy in " + 
            (System.nanoTime() - currentTime) / 1000000 + "ms"
        );
        
        currentTime = System.nanoTime();
        Logger.log(
            Logger.VERBOSITY_VERBOSE, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            "[OpenGL] Generating default texture graphics strategy..."
        );
        TextureGraphicsGL.generateDefault(this);
        Logger.log(
            Logger.VERBOSITY_STANDARD, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            "Generated default texture graphics strategy in " + 
            (System.nanoTime() - currentTime) / 1000000 + "ms"
        );
        
        Logger.log(
            Logger.VERBOSITY_STANDARD, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            "[OpenGL] Defaults generated!"
        );
    }

    @Override
    public void initialize() {
        this.generateDefaults();
        
        for( String passKey : this.renderPassManager.getOrder() )
        this.renderPassManager.getPass(passKey).prepare();
    }
    
    @Override
    public void generateRenderBuffers() {
        for( String passKey : this.renderPassManager.getOrder() )
        {
            IRenderPass renderPass = this.renderPassManager.getPass(passKey);
            renderPass.populateBuffer();
        }
    }
    
    @Override
    public void render() {
        this.graphicsAssetProcessor.processGraphicsRequests();
        
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
