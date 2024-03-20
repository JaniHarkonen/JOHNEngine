package johnengine.basic.opengl.renderer.strgui;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.JGUI;
import johnengine.basic.game.gui.CText;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.asset.MeshGraphicsGL;
import johnengine.basic.opengl.renderer.asset.Shader;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.uniforms.UNIInteger;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.uniforms.UNIVector3f;
import johnengine.basic.opengl.renderer.vao.VAO;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderSubmissionStrategy;
import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderPass;
import johnengine.core.renderer.IRenderer;
import johnengine.core.renderer.RenderBufferManager;
import johnengine.core.renderer.SubmissionStrategyManager;

public class GUIRenderPass implements
    IRenderPass,
    IHasRenderBuffer
{
    private final RendererGL renderer;
    private final VAOCache vaoCache;
    private ShaderProgram shaderProgram;
    private RenderBufferManager<RenderBuffer> renderBufferManager;
    private SubmissionStrategyManager submissionManager;
    
    private JGUI activeGUI;
    
    public GUIRenderPass(RendererGL renderer) {
        this.renderer = renderer;
        this.vaoCache = new VAOCache(10*1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferManager = new RenderBufferManager<>(new RenderBuffer());
        this.submissionManager = new SubmissionStrategyManager();
        this.activeGUI = null;
        
        this.submissionManager
        .addStrategy(CText.class, new SubmitText(this));
    }
    

    @Override
    public void prepare() {
        Shader vertexShader = new Shader(GL46.GL_VERTEX_SHADER, "vertex-shader", true, null);
        this.loadShader(vertexShader, "gui.vert");
        
        Shader fragmentShader = new Shader(GL46.GL_FRAGMENT_SHADER, "fragment-shader", true, null);
        this.loadShader(fragmentShader, "gui.frag");
        
            // Add shaders and generate shader program
        this.shaderProgram
        .addShader(vertexShader)
        .addShader(fragmentShader)
        .generate();
        
        UNIMatrix4f projectionMatrix = new UNIMatrix4f("projectionMatrix", "uProjectionMatrix");
        UNIVector3f textOffset = new UNIVector3f("textOffset", "uTextOffset");
        UNIInteger textureSampler = new UNIInteger("textureSampler", "uTextureSampler");
        
        this.shaderProgram
        .declareUniform(projectionMatrix)
        .declareUniform(textOffset)
        .declareUniform(textureSampler);
    }
    
    private void loadShader(Shader targetShader, String filename) {
        TextAsset.LoadTask loadTask = new TextAsset.LoadTask();
        loadTask.setTarget(targetShader);
        loadTask.setPath(this.renderer.getResourceRootFolder() + "shaders/" + filename);
        loadTask.load();
    }

    @Override
    public void newBuffer() {
        this.renderBufferManager.newBuffer();
    }

    @Override
    public boolean executeSubmissionStrategy(IRenderable target) {
        IRenderSubmissionStrategy<IRenderable> submissionStrategy = 
            this.submissionManager.getStrategy(target.getClass());
        
        if( submissionStrategy == null )
        return false;
        
        submissionStrategy.execute(target);
        return true;
    }

    @Override
    public void render() {
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);
        
        RenderBuffer renderBuffer = this.renderBufferManager.poll();
        
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
        float windowHorizontalCenter = this.renderer.getWindow().getWidth() / 2;
        float windowVerticalCenter = this.renderer.getWindow().getHeight() / 2;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix
        .identity()
        .setOrtho2D(
            -windowHorizontalCenter, 
            windowHorizontalCenter, 
            windowVerticalCenter, 
            -windowVerticalCenter
        );
        
        UNIMatrix4f.class.cast(this.shaderProgram.getUniform("projectionMatrix"))
        .set(projectionMatrix);
        
        for( RenderUnit renderUnit : renderBuffer.getBuffer() )
        {
            Font font = renderUnit.font;
            String[] lines = renderUnit.text.split("\n");
            
            for( int i = 0; i < lines.length; i++ )
            {
                String text = lines[i];
                for( int j = 0; j < text.length(); j++ )
                {
                    char character = text.charAt(j);
                    Mesh mesh = font.getGlyphMesh(character);
                    
                        // Determine text offset
                    UNIVector3f.class.cast(this.shaderProgram.getUniform("textOffset"))
                    .set(new Vector3f(j * 16, i * 16, 0.0f));
                    
                        // Bind texture
                    GL46.glActiveTexture(GL46.GL_TEXTURE0);
                    TextureGraphicsGL textureGL = (TextureGraphicsGL) font.getTexture().getGraphicsStrategy();
                    textureGL.bind();
                    
                        // Bind VAO
                    MeshGraphicsGL meshGL = (MeshGraphicsGL) mesh.getGraphicsStrategy();
                    VAO vao = this.vaoCache.fetchVAO(meshGL);
                    vao.bind();
                    
                    GL46.glDrawElements(
                        GL46.GL_TRIANGLES, 
                        mesh.getInfo().getAsset().get().getVertexCount() * 3, 
                        GL46.GL_UNSIGNED_INT, 
                        0
                    );
                }
            }
        }
        
        this.vaoCache.update();
    }

    @Override
    public void setRenderContext(IRenderContext renderContext) {
        this.activeGUI = (JGUI) renderContext;
    }
    
    @Override
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.renderBufferManager.getCurrentBuffer().setProjectionMatrix(projectionMatrix);
    }

    @Override
    public IRenderer getRenderer() {
        return this.renderer;
    }

    @Override
    public IRenderContext getRenderContext() {
        return this.activeGUI;
    }

    @Override
    public boolean canRender() {
        return (this.renderBufferManager.peekNext() != null);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    public void addRenderUnit(RenderUnit renderUnit) {
        this.renderBufferManager.getCurrentBuffer().addRenderUnit(renderUnit);
    }
}
