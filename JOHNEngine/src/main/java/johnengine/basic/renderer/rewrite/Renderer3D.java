package johnengine.basic.renderer.rewrite;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.asset.rewrite.MeshGL;
import johnengine.basic.renderer.asset.rewrite.TextureGL;
import johnengine.basic.renderer.strvaochc.rewrite.CachedVAORenderBufferStrategy;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;
import johnengine.core.winframe.AWindowFramework;

public class Renderer3D extends ARenderer {
    private JWorld activeWorld;
    
    public Renderer3D(AWindowFramework hostWindow, ARenderBufferStrategy renderBufferStrategy) {
        super(hostWindow, renderBufferStrategy);
        this.activeWorld = null;
    }
    
    public Renderer3D(AWindowFramework hostWindow) {
        this(hostWindow, new CachedVAORenderBufferStrategy());
        this.renderBufferStrategy.setRenderer(this);
    }
    
    
    @Override
    public void generateDefaults() {
        
            // Generate default assets that use OpenGL
        //Mesh.generateDefault();
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
        this.renderBufferStrategy.execute(this.activeWorld);
    }
    
    @Override
    public void render() {
        GL11.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        this.renderBufferStrategy.render(this);
        
        //this.uniformManager.getUniform("textureSampler").set();
        //this.activeShaderProgram.bind();
        //this.activeWorld.render(this);
        /*this.test.render(this);
        this.activeShaderProgram.unbind();*/
    }
    
    
    public void setActiveWorld(JWorld world) {
        if( world != null )
        this.activeWorld = world;
    }
    
    public JWorld getActiveWorld() {
        return this.activeWorld;
    }
}
