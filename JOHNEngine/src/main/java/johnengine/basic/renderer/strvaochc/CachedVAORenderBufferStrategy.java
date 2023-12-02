package johnengine.basic.renderer.strvaochc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRenderAsset;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.CModel;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.MeshGL.VBOContainer;
import johnengine.basic.renderer.asset.Texture;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.components.VAO;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.shader.Shader;
import johnengine.core.renderer.shader.ShaderProgram;
import johnengine.core.renderer.shader.uniforms.UNIInteger;

public class CachedVAORenderBufferStrategy extends ARenderBufferStrategy {
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    
    private final TimedCache<MeshGL, VAO> vaoCache;
    private final ShaderProgram shaderProgram;
    private final Map<Class<? extends IRenderAsset>, IGraphicsAsset<?>> graphicsAssetMap; 
    private final ConcurrentLinkedQueue<RenderBuffer> renderBufferQueue;
    private final ConcurrentLinkedQueue<IRenderAsset> assetGenerationQueue;
    private final ConcurrentLinkedQueue<IRenderAsset> assetDisposalQueue;
    private RenderBuffer currentRenderBuffer;
    private RenderBuffer lastRenderBuffer;

    public CachedVAORenderBufferStrategy() {
        super();
        this.vaoCache = new TimedCache<MeshGL, VAO>(DEFAULT_EXPIRATION_TIME * 1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferQueue = new ConcurrentLinkedQueue<RenderBuffer>();
        this.graphicsAssetMap = new HashMap<Class<? extends IRenderAsset>, IGraphicsAsset<?>>();
        this.assetGenerationQueue = new ConcurrentLinkedQueue<IRenderAsset>();
        this.assetDisposalQueue = new ConcurrentLinkedQueue<IRenderAsset>();
        this.currentRenderBuffer = null;
        this.lastRenderBuffer = null;
        
        this.addStrategoid(CModel.class, new StrategoidModel(this));
        this.addGraphicsAsset(Mesh.class, (IGraphicsAsset<?>) (new MeshGL()));
        this.addGraphicsAsset(Texture.class, (IGraphicsAsset<?>) (new TextureGL()));
    }
    
    
    private void addGraphicsAsset(Class<? extends IRenderAsset> renderAssetClass, IGraphicsAsset<?> graphicsAsset) {
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
        //UNIMatrix4f cameraProjectionMatrix = new UNIMatrix4f("cameraProjectionMatrix");
        UNIInteger textureSampler = new UNIInteger("texSampler");
        
        this.shaderProgram
        //.declareUniform(cameraOrientationMatrix)
        //.declareUniform(cameraProjectionMatrix)
        .declareUniform(textureSampler);
        
        //this.currentRenderBuffer = new RenderBuffer();
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
    public void assetLoaded(IRenderAsset asset) {
        this.assetGenerationQueue.add(asset);
    }
    
    @Override
    public void disposeAsset(IRenderAsset asset) {
        this.assetDisposalQueue.add(asset);
    }

    @Override
    public void processLoadedAssets() {
        IRenderAsset asset;
        while( (asset = this.assetGenerationQueue.poll()) != null )
        {
            IGraphicsAsset<?> graphicsAsset = this.graphicsAssetMap.get(asset.getClass());
            graphicsAsset.createInstance(asset).generate();
        }
        
        while( (asset = this.assetDisposalQueue.poll()) != null )
        asset.getGraphics().dispose();
    }
    
    @Override
    public void dispose() {
        this.shaderProgram.dispose();
    }
    
    public void render(ARenderer renderer) {
        RenderBuffer renderBuffer = this.lastRenderBuffer;
        RenderBuffer nextRenderBuffer = this.renderBufferQueue.poll();
        
        if( nextRenderBuffer != null )
        renderBuffer = nextRenderBuffer;
        
        if( renderBuffer == null )
        return;
        
            // Draw render units of all buffers
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("texSampler").set();
        
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
}
