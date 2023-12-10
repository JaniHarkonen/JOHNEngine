package johnengine.basic.renderer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.strvaochc.CachedVAORenderBufferStrategy;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;
import johnengine.core.winframe.AWindowFramework;

public class Renderer3D extends ARenderer {
    private JWorld activeWorld;
    private IRenderStrategy renderStrategy;
    private ARenderBufferStrategy renderBufferStrategy;
    
    public Renderer3D(AWindowFramework hostWindow, ARenderBufferStrategy renderBufferStrategy) {
        super(hostWindow);
        this.activeWorld = null;
        this.renderStrategy = new DefaultRenderStrategy();
        this.renderBufferStrategy = renderBufferStrategy;
    }
    
    public Renderer3D(AWindowFramework hostWindow) {
        this(hostWindow, null);
        this.renderBufferStrategy = new CachedVAORenderBufferStrategy(this);
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
        this.renderBufferStrategy.prepare();
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL13.glEnable(GL13.GL_MULTISAMPLE);
        //GL11.glCullFace(GL11.GL_BACK);
    }
    
    @Override
    public void generateRenderBuffer() {
        this.renderStrategy.execute(this.activeWorld, this.renderBufferStrategy);
    }
    
    @Override
    public void render() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        this.renderBufferStrategy.render();
    }
    
    
    public void setActiveWorld(JWorld world) {
        if( world != null )
        this.activeWorld = world;
    }
    
    public JWorld getActiveWorld() {
        return this.activeWorld;
    }
    
    public ARenderBufferStrategy getRenderBufferStrategy() {
        return this.renderBufferStrategy;
    }
}
