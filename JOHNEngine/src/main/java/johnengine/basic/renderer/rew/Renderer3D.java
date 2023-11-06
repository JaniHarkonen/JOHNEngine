package johnengine.basic.renderer.rew;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import johnengine.basic.game.JWorld;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.shdprog.rew.Shader;
import johnengine.core.renderer.shdprog.rew.ShaderProgram;
import johnengine.core.renderer.unimngr.UNIMatrix4f;
import johnengine.core.renderer.unimngr.UniformManager;
import johnengine.core.winframe.AWindowFramework;

public class Renderer3D extends ARenderer {
    
    private final ShaderProgram defaultShaderProgram;
    private JWorld activeWorld;
    private ShaderProgram activeShaderProgram;
    
    public Renderer3D(AWindowFramework hostWindow) {
        super(hostWindow, new UniformManager());
        
        this.uniformManager.declareUniform(new UNIMatrix4f("cameraOrientationMatrix", null));
        this.uniformManager.declareUniform(new UNIMatrix4f("cameraProjectionMatrix", null));
        
            // Default shader program with the hardcoded, default shaders
        this.defaultShaderProgram = new ShaderProgram(
            "cameraOrientationMatrix",
            "cameraProjectionMatrix"
        );
        this.defaultShaderProgram.setFragmentShader(
            new Shader(GL30.GL_FRAGMENT_SHADER,
            "default-fragment-shader",
            true,
            ""
        ));
        this.defaultShaderProgram.setVertexShader(
            new Shader(GL30.GL_VERTEX_SHADER,
            "default-vertex-shader",
            true,
            ""
        ));
    }
    

    @Override
    public void initialize() {
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL13.glEnable(GL13.GL_MULTISAMPLE);
        //GL11.glCullFace(GL11.GL_BACK);
        
        this.defaultShaderProgram.setup(this.uniformManager);
        this.activeShaderProgram = this.defaultShaderProgram;
    }
    
    @Override
    public void render() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        
        this.activeShaderProgram.bind();
        this.activeWorld.render(this);
        this.activeShaderProgram.unbind();
    }
    
    @Override
    public void newBuffer() {
        this.activeWorld.render(this);
        this.renderRequestManager.newBuffer();
        this.renderRequestManager.processRequests();
    }
    
    
    public void setActiveWorld(JWorld world) {
        if( world != null )
        this.activeWorld = world;
    }
    
    public JWorld getActiveWorld() {
        return this.activeWorld;
    }
}
