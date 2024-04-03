package johnengine.basic.opengl.renderer.cachedvao;

import java.util.Map;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.opengl.renderer.DefaultRenderBufferPopulator;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.asset.Shader;
import johnengine.basic.opengl.renderer.asset.TextureGraphicsGL;
import johnengine.basic.opengl.renderer.cachedvao.structs.SMaterial;
import johnengine.basic.opengl.renderer.cachedvao.structs.SPointLight;
import johnengine.basic.opengl.renderer.cachedvao.structs.SSpotLight;
import johnengine.basic.opengl.renderer.cachedvao.uniforms.UNIAmbientLight;
import johnengine.basic.opengl.renderer.cachedvao.uniforms.UNIDirectionalLight;
import johnengine.basic.opengl.renderer.cachedvao.uniforms.UNIMaterial;
import johnengine.basic.opengl.renderer.cachedvao.uniforms.UNIPointLight;
import johnengine.basic.opengl.renderer.cachedvao.uniforms.UNISpotLight;
import johnengine.basic.opengl.renderer.uniforms.UNIInteger;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.uniforms.UniformArray;
import johnengine.basic.opengl.renderer.vao.VAO;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderPass;
import johnengine.core.renderer.IRenderSubmissionStrategy;
import johnengine.core.renderer.RenderBufferManager;
import johnengine.core.renderer.SubmissionStrategyManager;

public class CachedVAORenderPass implements IRenderPass {
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    public static final int MAX_POINT_LIGHT_COUNT = 5;
    public static final int MAX_SPOT_LIGHT_COUNT = 5;
    
    private final RendererGL renderer;
    private final VAOCache vaoCache;
    private final ShaderProgram shaderProgram;
    private RenderBufferManager<RenderBuffer> renderBufferManager;
    private SubmissionStrategyManager submissionManager;
    private DefaultRenderBufferPopulator renderBufferPopulator;
    
    private JWorld activeWorld;
    
    public CachedVAORenderPass(RendererGL renderer) {
        this.renderer = renderer;
        this.vaoCache = new VAOCache(DEFAULT_EXPIRATION_TIME * 1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferManager = new RenderBufferManager<>(new RenderBuffer());
        this.activeWorld = null;
        this.renderBufferPopulator = new DefaultRenderBufferPopulator();
        
        this.submissionManager = new SubmissionStrategyManager()
        .addStrategy(CModel.class, new SubmitModel(this))
        .addStrategy(JCamera.class, new SubmitCamera(this))
        .addStrategy(JAmbientLight.class, new SubmitAmbientLight(this))
        .addStrategy(JPointLight.class, new SubmitPointLight(this))
        .addStrategy(JDirectionalLight.class, new SubmitDirectionalLight(this))
        .addStrategy(JSpotLight.class, new SubmitSpotLight(this));
    }
    
    
    @Override
    public void prepare() {
        
            // Load shaders
        Shader vertexShader = new Shader(
            GL46.GL_VERTEX_SHADER, 
            "vertex-shader", 
            true, 
            null
        );
        this.loadShader(vertexShader, "default.vert");
        
        Shader fragmentShader = new Shader(
            GL46.GL_FRAGMENT_SHADER, 
            "fragment-shader", 
            true, 
            null
        );
        this.loadShader(fragmentShader, "default.frag");
        
            // Add shaders
        this.shaderProgram
        .addShader(vertexShader)
        .addShader(fragmentShader);
        
            // Generate shader program
        this.shaderProgram.generate();
        
            // Declare uniforms
        UNIInteger textureSampler = new UNIInteger(
            "textureSampler", "uTextureSampler"
        );
        UNIInteger normalSampler = new UNIInteger(
            "normalSampler", "uNormalSampler"
        );
        UNIInteger roughnessSampler = new UNIInteger(
            "roughnessSampler", "uRoughnessSampler"
        );
        UNIMatrix4f projectionMatrix = new UNIMatrix4f(
            "projectionMatrix", "uProjectionMatrix"
        );
        UNIMatrix4f cameraMatrix = new UNIMatrix4f("cameraMatrix", "uCameraMatrix");
        UNIMatrix4f modelMatrix = new UNIMatrix4f("modelMatrix", "uModelMatrix");
        
        UNIAmbientLight ambientLight = new UNIAmbientLight(
            "ambientLight", "uAmbientLight"
        );
        UNIDirectionalLight directionalLight = new UNIDirectionalLight(
            "directionalLight", "uDirectionalLight"
        );
        UNIMaterial material = new UNIMaterial("material", "uMaterial");
        UniformArray<SPointLight, UNIPointLight> pointLight = 
            new UniformArray<SPointLight, UNIPointLight>(
                "pointLight", 
                "uPointLight",
                new UNIPointLight[MAX_POINT_LIGHT_COUNT]
            );
        
        UniformArray<SSpotLight, UNISpotLight> spotLight = 
            new UniformArray<SSpotLight, UNISpotLight>(
                "spotLight", 
                "uSpotLight",
                new UNISpotLight[MAX_SPOT_LIGHT_COUNT]
            );
        
        pointLight.fill(() -> new UNIPointLight());
        spotLight.fill(() -> new UNISpotLight());

        this.shaderProgram
        .declareUniform(textureSampler)
        .declareUniform(normalSampler)
        .declareUniform(roughnessSampler)
        .declareUniform(projectionMatrix)
        .declareUniform(cameraMatrix)
        .declareUniform(modelMatrix)
        .declareUniform(ambientLight)
        .declareUniform(material)
        .declareUniform(pointLight)
        .declareUniform(directionalLight)
        .declareUniform(spotLight);
        
            // OpenGL configuration
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL13.glEnable(GL13.GL_MULTISAMPLE);
        //GL11.glCullFace(GL11.GL_BACK);
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
        this.renderBufferPopulator.execute(this);
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
    
    @SuppressWarnings("unchecked")
    public void preRender(RenderBuffer renderBuffer) {
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
        ((UNIInteger) this.shaderProgram.getUniform("normalSampler"))
        .set(1);
        
        ((UNIInteger) this.shaderProgram.getUniform("roughnessSampler"))
        .set(2);
        
        ((UNIMatrix4f) this.shaderProgram.getUniform("projectionMatrix"))
        .set(renderBuffer.getProjectionMatrix());
        
        ((UNIMatrix4f) this.shaderProgram.getUniform("cameraMatrix"))
        .set(renderBuffer.getCameraMatrix());
        
        ((UNIAmbientLight) this.shaderProgram.getUniform("ambientLight"))
        .set(renderBuffer.getAmbientLight());
        
        ((UNIDirectionalLight) this.shaderProgram.getUniform("directionalLight"))
        .set(renderBuffer.getDirectionalLight());
        
            // Setup point light uniforms
        int pointLightIndex = 0;
        for( Map.Entry<JPointLight, SPointLight> en : renderBuffer.getPointLights() )
        {
            SPointLight struct = en.getValue();
            
            UNIPointLight uPointLight = (
                (UniformArray<SPointLight, UNIPointLight>) 
                this.shaderProgram.getUniform("pointLight")
            ).getArrayIndex(pointLightIndex);
            
            uPointLight.set(struct);
            pointLightIndex++;
        }
        
            // Setup spot light uniforms
        int spotLightIndex = 0;
        for( Map.Entry<JSpotLight, SSpotLight> en : renderBuffer.getSpotLights() )
        {
            SSpotLight struct = en.getValue();
            
            UNISpotLight uSpotLight = (
                (UniformArray<SSpotLight, UNISpotLight>) 
                this.shaderProgram.getUniform("spotLight")
            ).getArrayIndex(spotLightIndex);
            
            uSpotLight.set(struct);
            spotLightIndex++;
        }
    }
    
    @Override
    public void render() {
        GL46.glEnable(GL46.GL_DEPTH_TEST);
        
            // Pre-render
        RenderBuffer renderBuffer = this.renderBufferManager.poll();
        this.preRender(renderBuffer);
        
            // Issue draw calls based on the render units
        UNIMatrix4f modelMatrix = 
            (UNIMatrix4f) this.shaderProgram.getUniform("modelMatrix");
        for( RenderUnit unit : renderBuffer.getBuffer() )
        {
                // Set position matrix
            modelMatrix.set(unit.positionMatrix);
            
                // Create the material struct
            Material material = unit.material;
            SMaterial materialStruct = new SMaterial();
            materialStruct.c4Diffuse = material.getDiffuseColor();
            materialStruct.c4Specular = material.getSpecularColor();
            materialStruct.fReflectance = material.getReflectance();
            
                // Get and bind texture
            Texture texture = material.getTexture();
            Texture normalMap = material.getNormalMap();
            Texture roughnessMap = material.getRoughnessMap();
            TextureGraphicsGL textureGraphics = 
                (TextureGraphicsGL) texture.getGraphicsStrategy();
            ((UNIMaterial) this.shaderProgram.getUniform("material"))
            .set(materialStruct);
            
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            textureGraphics.bind();
            
            if( normalMap != null )
            {
                GL46.glActiveTexture(GL46.GL_TEXTURE1);
                ((TextureGraphicsGL) normalMap.getGraphicsStrategy()).bind();
            }
            
            if( roughnessMap != null )
            {
                GL46.glActiveTexture(GL46.GL_TEXTURE2);
                ((TextureGraphicsGL) roughnessMap.getGraphicsStrategy()).bind();
            }
            
                // Bind VAO and issue a draw call
            VAO vao = this.vaoCache.fetchVAO(unit.meshGraphics);
            vao.bind();
            GL46.glDrawElements(
                GL46.GL_TRIANGLES,
                unit.meshData.getVertexCount() * 3,
                GL46.GL_UNSIGNED_INT,
                0
            );
        }
        
            // Post-render
        this.postRender();
    }
    
    public void postRender() {
        GL46.glBindVertexArray(0);
        this.shaderProgram.unbind();
        
            // Update cached VAOs and remove those that have been unused
            // for too long
        this.vaoCache.update();
        //((UniformArray<SPointLight, UNIPointLight>) this.shaderProgram.getUniform("pointLight"))
        //.fill(() -> );
    }
    
    @Override
    public void dispose() {
        this.shaderProgram.dispose();
    }
    
    RenderBuffer getCurrentRenderBuffer() {
        return this.renderBufferManager.getCurrentBuffer();
    }

    @Override
    public void setRenderContext(IRenderContext activeWorld) {
        if( activeWorld != null )
        this.activeWorld = (JWorld) activeWorld;
    }

    @Override
    public RendererGL getRenderer() {
        return this.renderer;
    }
    
    @Override
    public IRenderContext getRenderContext() {
        return this.activeWorld;
    }
    
    @Override
    public boolean canRender() {
        return (this.renderBufferManager.peekNext() != null);
    }
}
