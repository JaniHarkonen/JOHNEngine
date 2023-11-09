package johnengine.basic.renderer;

import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.imgasset.Texture;
import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.components.VertexArrayObject;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.shdprog.Shader;
import johnengine.core.renderer.shdprog.ShaderProgram;
import johnengine.core.renderer.unimngr.UNIMatrix1I;
import johnengine.core.renderer.unimngr.UNIMatrix4f;
import johnengine.core.renderer.unimngr.UniformManager;
import johnengine.core.winframe.AWindowFramework;
import johnengine.testing.DebugUtils;

public class Renderer3D extends ARenderer {
    
    public static final VertexArrayObject DEFAULT_VAO = new VertexArrayObject();
    public static final Texture DEFAULT_TEXTURE = new Texture("default-texture", true);
    public static Mesh test;
    
    private final ShaderProgram defaultShaderProgram;
    private JWorld activeWorld;
    private ShaderProgram activeShaderProgram;
    
    public Renderer3D(AWindowFramework hostWindow) {
        super(hostWindow, new UniformManager());
        
            // Default shader program with the hardcoded, default shaders
        this.defaultShaderProgram = new ShaderProgram(
            "cameraOrientationMatrix",
            "cameraProjectionMatrix"
        );
    }
    

    @Override
    public void initialize() {
        GL.createCapabilities();
        
        this.uniformManager.declareUniform(new UNIMatrix4f("cameraOrientationMatrix", null));
        this.uniformManager.declareUniform(new UNIMatrix4f("cameraProjectionMatrix", null));
        this.uniformManager.declareUniform(new UNIMatrix1I("textureSampler", 0));
        
            // Default vertex shader
        try {
            this.defaultShaderProgram.setVertexShader(
                new Shader(GL30.GL_VERTEX_SHADER,
                "default-vertex-shader",
                true,
                Files.readAllBytes(Path.of("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default.vert")).toString()
            ));
            
            this.defaultShaderProgram.setFragmentShader(
                new Shader(GL30.GL_FRAGMENT_SHADER,
                "default-fragment-shader",
                true,
                Files.readAllBytes(Path.of("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default.frag")).toString()
            ));
        }
        catch( Exception e )
        {
            DebugUtils.log(this, "unlucky gg");
        }
        
        
            // Default fragment shader
        
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        DEFAULT_VAO.generate();
        DEFAULT_TEXTURE.generate();
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL13.glEnable(GL13.GL_MULTISAMPLE);
        //GL11.glCullFace(GL11.GL_BACK);
        
        this.defaultShaderProgram.setup(this.uniformManager);
        this.activeShaderProgram = this.defaultShaderProgram;
        
        this.test = new Mesh("lol");
    }
    
    @Override
    public void render() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, this.hostWindow.getWidth(), this.hostWindow.getHeight());
        
        this.uniformManager.getUniform("textureSampler").set();
        this.activeShaderProgram.bind();
        this.activeWorld.render(this);
        this.test.render(this);
        this.activeShaderProgram.unbind();
    }
    
    
    public void setActiveWorld(JWorld world) {
        if( world != null )
        this.activeWorld = world;
    }
    
    public JWorld getActiveWorld() {
        return this.activeWorld;
    }
}
