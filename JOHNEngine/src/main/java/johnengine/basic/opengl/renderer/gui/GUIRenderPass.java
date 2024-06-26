package johnengine.basic.opengl.renderer.gui;

import java.util.ArrayDeque;
import java.util.Deque;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.gui.JButton;
import johnengine.basic.game.gui.JForm;
import johnengine.basic.game.gui.JFrame;
import johnengine.basic.game.gui.JGUI;
import johnengine.basic.game.gui.JImage;
import johnengine.basic.game.gui.JText;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.asset.Shader;
import johnengine.basic.opengl.renderer.uniforms.UNIInteger;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.uniforms.UNIVector4f;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderPass;
import johnengine.core.renderer.IRenderSubmissionStrategy;
import johnengine.core.renderer.IRenderer;
import johnengine.core.renderer.RenderBufferManager;
import johnengine.core.renderer.SubmissionStrategyManager;

public class GUIRenderPass implements IRenderPass {
    private final RendererGL renderer;
    private final VAOCache vaoCache;
    private ShaderProgram shaderProgram;
    private RenderBufferManager<DOM> renderBufferManager;
    private SubmissionStrategyManager submissionManager;
    private DOMPopulator domPopulator;
    
    private JGUI activeGUI;
    
    public GUIRenderPass(RendererGL renderer) {
        this.renderer = renderer;
        this.vaoCache = new VAOCache(10*1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferManager = new RenderBufferManager<>(new DOM());
        this.submissionManager = new SubmissionStrategyManager();
        this.domPopulator = new DOMPopulator();
        
        this.activeGUI = null;
        
        this.submissionManager
        .addStrategy(JFrame.class, new SubmitFrame(this))
        .addStrategy(JForm.class, new SubmitForm(this))
        .addStrategy(JImage.class, new SubmitImage(this))
        .addStrategy(JText.class, new SubmitText(this))
        .addStrategy(JButton.class, new SubmitButton(this));
    }
    

    @Override
    public void prepare() {
        Shader vertexShader = 
            new Shader(GL46.GL_VERTEX_SHADER, "vertex-shader", true, null);
        this.loadShader(vertexShader, "gui.vert");
        
        Shader fragmentShader = 
            new Shader(GL46.GL_FRAGMENT_SHADER, "fragment-shader", true, null);
        this.loadShader(fragmentShader, "gui.frag");
        
            // Add shaders and generate shader program
        this.shaderProgram
        .addShader(vertexShader)
        .addShader(fragmentShader)
        .generate();
        
        UNIMatrix4f projectionMatrix = 
            new UNIMatrix4f("projectionMatrix", "uProjectionMatrix");
        UNIInteger textureSampler = 
            new UNIInteger("textureSampler", "uTextureSampler");
        UNIMatrix4f modelMatrix =
            new UNIMatrix4f("modelMatrix", "uModelMatrix");
        UNIInteger hasTexture =
            new UNIInteger("hasTexture", "uHasTexture");
        UNIVector4f elementColor =
            new UNIVector4f("elementColor", "uElementColor");
        UNIVector4f textColor = 
            new UNIVector4f("textColor", "uTextColor");
        
        this.shaderProgram
        .declareUniform(projectionMatrix)
        .declareUniform(textureSampler)
        .declareUniform(modelMatrix)
        .declareUniform(hasTexture)
        .declareUniform(elementColor)
        .declareUniform(textColor);
    }
    
    private void loadShader(Shader targetShader, String filename) {
        TextAsset.LoadTask loadTask = new TextAsset.LoadTask();
        loadTask.setTarget(targetShader);
        loadTask.setPath(
            this.renderer.getResourceRootFolder() + "shaders/" + filename
        );
        loadTask.load();
    }

    @Override
    public void newBuffer() {
        this.renderBufferManager.newBuffer();
    }
    
    @Override
    public void populateBuffer() {
        this.domPopulator.execute(this);
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
    
    public void preRender() {
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);
        
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
        float windowHorizontalCenter = this.renderer.getWindow().getWidth() / 2;
        float windowVerticalCenter = this.renderer.getWindow().getHeight() / 2;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix
        .identity()
        .setOrtho2D(
            0, 
            windowHorizontalCenter * 2, 
            windowVerticalCenter * 2, 
            0
        );
        
        UNIMatrix4f.class.cast(this.shaderProgram.getUniform("projectionMatrix"))
        .set(projectionMatrix);
    }

    @Override
    public void render() {
        DOM dom = this.renderBufferManager.poll();
        GL46.glDisable(GL46.GL_DEPTH_TEST);
        
        this.preRender();
        
        Deque<RendererContext> contextStack = new ArrayDeque<>();
        RendererContext context = new RendererContext(
            this.shaderProgram, this.vaoCache
        );
        
        for( DOM.Node frameNode : dom.getFrameNodes() )
        {
            context.font = frameNode.submission.font;
            
            for( DOM.Node node : frameNode.children )
            this.renderChildrenRecursively(node, context, contextStack);
        }
        
        this.vaoCache.update();
    }
    
    private void renderChildrenRecursively(
        DOM.Node rootNode, 
        RendererContext context,
        Deque<RendererContext> contextStack
    ) {
        contextStack.push(context);
        
        RendererContext newContext = new RendererContext(
            context.shaderProgram, context.vaoCache
        ); 
        newContext.setFont(rootNode.submission.font, context.font);
        newContext.setColor(
            rootNode.submission.color, context.color
        );
        newContext.setTextColor(
            rootNode.submission.textColor, context.textColor
        );
        context = newContext;
        
        rootNode.submission.render(context);
        
        for( DOM.Node node : rootNode.children )
        this.renderChildrenRecursively(node, context, contextStack);
        
        contextStack.pop();
    }

    @Override
    public void setRenderContext(IRenderContext renderContext) {
        this.activeGUI = (JGUI) renderContext;
    }
    
    DOM getCurrentDOM() {
        return this.renderBufferManager.getCurrentBuffer();
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
        this.shaderProgram.dispose();
    }
}
