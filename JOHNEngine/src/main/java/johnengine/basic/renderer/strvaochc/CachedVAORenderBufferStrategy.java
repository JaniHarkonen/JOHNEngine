package johnengine.basic.renderer.strvaochc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.asset.ARendererAsset;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.MeshGL.VBOContainer;
import johnengine.basic.renderer.asset.Shader;
import johnengine.basic.renderer.asset.Texture;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.renderer.strvaochc.structs.SMaterial;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;
import johnengine.basic.renderer.strvaochc.uniforms.rewrite.UNIAmbientLight;
import johnengine.basic.renderer.strvaochc.uniforms.rewrite.UNIMaterial;
import johnengine.basic.renderer.strvaochc.uniforms.rewrite.UNIPointLight;
import johnengine.basic.renderer.uniforms.UNIInteger;
import johnengine.basic.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.renderer.uniforms.UniformArray;
import johnengine.basic.renderer.uniforms.UniformUtils;
import johnengine.basic.renderer.vertex.VAO;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;

public class CachedVAORenderBufferStrategy extends ARenderBufferStrategy {
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    public static final int MAX_POINT_LIGHT_COUNT = 5;
    
    private static final SAmbientLight STRUCT_DEFAULT_AMBIENT_LIGHT = new SAmbientLight(
        JAmbientLight.DEFAULT_COLOR, JAmbientLight.DEFAULT_INTENSITY
    );
    
    private final TimedCache<MeshGL, VAO> vaoCache;
    private final ShaderProgram shaderProgram;
    private final Map<Class<? extends IRendererAsset>, IGraphicsAsset<?>> graphicsAssetMap; 
    private final ConcurrentLinkedQueue<RenderBuffer> renderBufferQueue;
    private final ConcurrentLinkedQueue<IRendererAsset> assetGenerationQueue;
    private final ConcurrentLinkedQueue<IRendererAsset> assetDisposalQueue;
    
    private RenderBuffer currentRenderBuffer;
    private RenderBuffer lastRenderBuffer;
    
    private Matrix4f projectionMatrix;
    private Matrix4f cameraMatrix;
        
    private SAmbientLight ambientLight;
    private Map<JPointLight, SPointLight> pointLights;

    public CachedVAORenderBufferStrategy(ARenderer renderer) {
        super(renderer);
        this.vaoCache = new TimedCache<>(DEFAULT_EXPIRATION_TIME * 1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferQueue = new ConcurrentLinkedQueue<>();
        this.graphicsAssetMap = new HashMap<>();
        this.assetGenerationQueue = new ConcurrentLinkedQueue<>();
        this.assetDisposalQueue = new ConcurrentLinkedQueue<>();
        this.currentRenderBuffer = null;
        this.lastRenderBuffer = null;
        this.projectionMatrix = new Matrix4f();
        this.cameraMatrix = new Matrix4f();
        this.ambientLight = null;
        this.pointLights = new HashMap<>();
        
        this.addStrategoid(CModel.class, new StrategoidModel(this));
        this.addStrategoid(JCamera.class, new StrategoidCamera(this));
        this.addStrategoid(JAmbientLight.class, new StrategoidAmbientLight(this));
        this.addStrategoid(JPointLight.class, new StrategoidPointLight(this));
        
        this.addGraphicsAsset(Mesh.class, (IGraphicsAsset<?>) (new MeshGL()));
        this.addGraphicsAsset(Texture.class, (IGraphicsAsset<?>) (new TextureGL()));
    }
    
    
    private void addGraphicsAsset(Class<? extends IRendererAsset> renderAssetClass, IGraphicsAsset<?> graphicsAsset) {
        this.graphicsAssetMap.put(renderAssetClass, graphicsAsset);
    }
    
    @Override
    public void prepare() {
        
            // Load shaders
        Shader vertexShader = new Shader(GL30.GL_VERTEX_SHADER, "vertex-shader", true, null);
        Shader fragmentShader = new Shader(GL30.GL_FRAGMENT_SHADER, "fragment-shader", true, null);
        
        TextAsset.Loader loader = new TextAsset.Loader();
        loader.setTarget(vertexShader);
        loader.setPath("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default.vert");
        loader.load();
        
        loader.setTarget(fragmentShader);
        loader.setPath("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default.frag");
        loader.load();
        
            // Add shaders
        this.shaderProgram
        .addShader(vertexShader)
        .addShader(fragmentShader);
        
            // Generate shader program
        this.shaderProgram.generate();
        
            // Declare uniforms
        UNIInteger textureSampler = new UNIInteger("textureSampler", "uTextureSampler");
        UNIMatrix4f projectionMatrix = new UNIMatrix4f("projectionMatrix", "uProjectionMatrix");
        UNIMatrix4f cameraMatrix = new UNIMatrix4f("cameraMatrix", "uCameraMatrix");
        UNIMatrix4f modelMatrix = new UNIMatrix4f("modelMatrix", "uModelMatrix");
        
        UNIAmbientLight ambientLight = new UNIAmbientLight("ambientLight", "uAmbientLight");
        UNIMaterial material = new UNIMaterial("material", "uMaterial");
        UniformArray<SPointLight, UNIPointLight> pointLight = 
            new UniformArray<SPointLight, UNIPointLight>(
                "pointLight", 
                "uPointLight",
                new UNIPointLight[MAX_POINT_LIGHT_COUNT]
            );
        
        UniformUtils.fillArray(pointLight, () -> new UNIPointLight("", ""));

        
        this.shaderProgram
        .declareUniform(textureSampler)
        .declareUniform(projectionMatrix)
        .declareUniform(cameraMatrix)
        .declareUniform(modelMatrix)
        .declareUniform(ambientLight)
        .declareUniform(material)
        .declareUniform(pointLight);
    }
    
    @Override
    public void startBuffer() {
        this.currentRenderBuffer = new RenderBuffer();
    }
    
    @Override
    public void endBuffer() {
        this.renderBufferQueue.add(this.currentRenderBuffer);
    }
    
    @Override
    public void assetLoaded(IRendererAsset asset) {
        this.assetGenerationQueue.add(asset);
    }
    
    @Override
    public void deloadAsset(IRendererAsset asset) {
        this.assetDisposalQueue.add(asset);
    }


    @Override
    public void processAssetDeloads() {
        IRendererAsset asset;
        while( (asset = this.assetDisposalQueue.poll()) != null )
        asset.getGraphics().dispose();
    }

    @Override
    public void processLoadedAssets() {
        IRendererAsset asset;
        while( (asset = this.assetGenerationQueue.poll()) != null )
        {
            IGraphicsAsset<?> graphicsAsset = this.graphicsAssetMap.get(asset.getClass());
            graphicsAsset.createInstance(asset).generate();
            
            ((ARendererAsset<?, ?>) asset).setDeloader(this);
        }
    }
    
    @Override
    public void dispose() {
        this.shaderProgram.dispose();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void render() {
        this.processLoadedAssets();
        this.processAssetDeloads();
        
        RenderBuffer renderBuffer = this.lastRenderBuffer;
        RenderBuffer nextRenderBuffer = this.renderBufferQueue.poll();
        
        if( nextRenderBuffer != null )
        renderBuffer = nextRenderBuffer;
        
        if( renderBuffer == null )
        return;
        
            // Draw render units of all buffers
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
            // Setup view matrix uniforms
        ((UNIMatrix4f) this.shaderProgram.getUniform("projectionMatrix")).set(this.projectionMatrix);
        ((UNIMatrix4f) this.shaderProgram.getUniform("cameraMatrix")).set(this.cameraMatrix);
        
        SAmbientLight structAmbientLight = STRUCT_DEFAULT_AMBIENT_LIGHT;
        
            // Setup ambient light uniforms
        if( this.ambientLight != null )
        structAmbientLight = this.ambientLight;
        
        ((UNIAmbientLight) this.shaderProgram.getUniform("ambientLight")).set(structAmbientLight);
        
            // Setup point light uniforms
        int pointLightIndex = 0;
        for( Map.Entry<JPointLight, SPointLight> en : this.pointLights.entrySet() )
        {
            SPointLight struct = en.getValue();
            UNIPointLight uPointLight = ((UniformArray<SPointLight, UNIPointLight>) this.shaderProgram.getUniform("pointLight"))
            .getArrayIndex(pointLightIndex);
            uPointLight.set(struct);
        }
        
        UNIMatrix4f modelMatrix = (UNIMatrix4f) this.shaderProgram.getUniform("modelMatrix");
        
        do
        {
            for( RenderUnit unit : renderBuffer.getBuffer() )
            {
                    // Get VAO and mesh data
                Mesh mesh = unit.getMesh();
                Mesh.Data meshData = unit.getMesh().getData();
                
                MeshGL meshGraphics = (MeshGL) mesh.getGraphics();
                VAO vao = this.vaoCache.get(meshGraphics);
                
                    // If a cache for the VAO of this mesh cannot be found, create it and
                    // store it
                if( vao == null )
                {
                    vao = new VAO();
                    VBOContainer vbos = meshGraphics.getData();
                    vao.addVBO(vbos.getVerticesVBO());
                    vao.addVBO(vbos.getNormalsVBO());   
                    vao.addVBO(vbos.getTextureCoordinatesVBO());
                    vao.setIndicesVBO(vbos.getIndicesVBO());
                    vao.generate();
                    
                    this.vaoCache.cacheItem(meshGraphics, vao);
                }
                
                    // Set position matrix
                modelMatrix.set(unit.getPositionMatrix());
                
                    // Bind texture and set material
                Material material = mesh.getMaterial();
                Texture texture = material.getTexture();
                TextureGL textureGraphics = (TextureGL) texture.getGraphics();
                
                SMaterial materialStruct = new SMaterial();
                materialStruct.c4Diffuse = material.getDiffuseColor();
                materialStruct.c4Specular = material.getSpecularColor();
                materialStruct.fReflectance = material.getReflectance();
                
                ((UNIMaterial) this.shaderProgram.getUniform("material"))
                .set(materialStruct);
                GL30.glActiveTexture(GL30.GL_TEXTURE0);
                textureGraphics.bind();
                
                    // Bind mesh and draw
                vao.bind();
                GL30.glDrawElements(GL30.GL_TRIANGLES, meshData.getVertexCount() * 3, GL30.GL_UNSIGNED_INT, 0);
            }
            
            this.lastRenderBuffer = renderBuffer;
        }
        while( (renderBuffer = this.renderBufferQueue.poll()) != null );
        
        GL30.glBindVertexArray(0);
        this.shaderProgram.unbind();
        
            // Update cached VAOs and remove those that have been unused
            // for too long
        this.vaoCache.update();
    }
    
    
    public void addRenderUnit(RenderUnit unit) {
        this.currentRenderBuffer.addUnit(unit);
    }
    
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    
    public void setCameraMatrix(Matrix4f cameraMatrix) {
        this.cameraMatrix = cameraMatrix;
    }
    
    public void setAmbientLight(SAmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }
    
    public void addPointLight(JPointLight pointLight, SPointLight pointLightStruct) {
        this.pointLights.put(pointLight, pointLightStruct);
    }
}
