package johnengine.core.renderer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import johnengine.basic.game.JCamera;
import johnengine.core.renderer.shdprog.ShaderProgram;
import johnengine.core.renderer.unimngr.UNIMatrix4f;
import johnengine.core.renderer.unimngr.UniformManager;
import johnengine.core.winframe.AWindowFramework;

public class Renderer3D {
    
    private AWindowFramework hostWindow;
    private final Matrix4f projectionMatrix;
    private JCamera activeCamera;
    private UniformManager uniformManager;
    private ShaderProgram activeShaderProgram;
    
    public Renderer3D(AWindowFramework hostWindow) {
        this.hostWindow = hostWindow;
        this.projectionMatrix = (new Matrix4f()).setPerspective(
            (float) Math.toRadians(90.0f), 
            this.hostWindow.getWidth() / this.hostWindow.getHeight(), 
            0.01f, 
            1000.0f
        );
        this.uniformManager = new UniformManager();
        this.uniformManager.declareUniform(new UNIMatrix4f("projectionMatrix", this.projectionMatrix));
    }
    

    public void initialize() {
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL13.glEnable(GL13.GL_MULTISAMPLE);
        //GL11.glCullFace(GL11.GL_BACK);
    }
    
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        
        this.activeShaderProgram.bind();
        
        this.activeShaderProgram.unbind();
    }
}
