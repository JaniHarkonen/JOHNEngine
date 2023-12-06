package johnengine.basic.renderer.strvaochc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.CModel;
import johnengine.basic.game.rewrite.JCamera;
import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.asset.ARendererAsset;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.MeshGL.VBOContainer;
import johnengine.basic.renderer.asset.Shader;
import johnengine.basic.renderer.asset.Texture;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.components.VAO;
import johnengine.basic.renderer.components.uniforms.UNIInteger;
import johnengine.basic.renderer.components.uniforms.UNIMatrix4f;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;

public class CachedVAORenderBufferStrategy extends ARenderBufferStrategy {
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    
    private final TimedCache<MeshGL, VAO> vaoCache;
    private final ShaderProgram shaderProgram;
    private final Map<Class<? extends IRendererAsset>, IGraphicsAsset<?>> graphicsAssetMap; 
    private final ConcurrentLinkedQueue<RenderBuffer> renderBufferQueue;
    private final ConcurrentLinkedQueue<IRendererAsset> assetGenerationQueue;
    private final ConcurrentLinkedQueue<IRendererAsset> assetDisposalQueue;
    
    private RenderBuffer currentRenderBuffer;
    private RenderBuffer lastRenderBuffer;
    
    private Matrix4f cameraMatrix;
    private JCamera activeCamera;

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
        this.activeCamera = null;
        this.cameraMatrix = new Matrix4f();
        
        this.addStrategoid(CModel.class, new StrategoidModel(this));
        this.addStrategoid(JCamera.class, new StrategoidCamera(this));
        
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
        //UNIMatrix4f cameraOrientationMatrix = new UNIMatrix4f("cameraOrientationMatrix");
        
        UNIInteger textureSampler = new UNIInteger("textureSampler");
        UNIMatrix4f cameraMatrix = new UNIMatrix4f("cameraMatrix");
        UNIMatrix4f cameraOrientationMatrix = new UNIMatrix4f("cameraOrientationMatrix");
        
        this.shaderProgram
        //.declareUniform(cameraOrientationMatrix)
        .declareUniform(textureSampler)
        .declareUniform(cameraMatrix)
        .declareUniform(cameraOrientationMatrix);
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
        JCamera camera = this.activeCamera;
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
        if( camera != null )
        ((UNIMatrix4f) this.shaderProgram.getUniform("cameraMatrix")).set(this.cameraMatrix);
        
        do
        {
            for( RenderUnit unit : renderBuffer.getBuffer() )
            {
                Mesh mesh = unit.getMesh();
                Texture texture = unit.getTexture();
                
                MeshGL meshGraphics = (MeshGL) mesh.getGraphics();
                TextureGL textureGraphics = (TextureGL) texture.getGraphics();
                VAO vao = this.vaoCache.get(meshGraphics);
                
                    // If a cache for the VAO of this mesh cannot be found, create it and
                    // store it
                if( vao == null )
                {
                    vao = new VAO();
                    VBOContainer vbos = meshGraphics.getData();
                    vao.addVBO(vbos.getVerticesVBO());
                    vao.addVBO(vbos.getTextureCoordinatesVBO());
                    vao.setIndicesVBO(vbos.getIndicesVBO());
                    vao.generate();
                    
                    this.vaoCache.cacheItem(meshGraphics, vao);
                }
                
                    // Bind texture
                GL30.glActiveTexture(GL30.GL_TEXTURE0);
                textureGraphics.bind();
                
                    // Bind mesh and draw
                vao.bind();
                GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.getData().getVertexCount() * 3, GL30.GL_UNSIGNED_INT, 0);
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
    
    public void setActiveCamera(JCamera camera) {
        this.activeCamera = camera;
    }
    
    public void setCameraMatrix(Matrix4f cameraMatrix) {
        this.cameraMatrix = cameraMatrix;
    }
}
