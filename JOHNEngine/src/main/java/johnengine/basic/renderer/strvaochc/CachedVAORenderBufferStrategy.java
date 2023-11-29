package johnengine.basic.renderer.strvaochc;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.CModel;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.Mesh.VBOContainer;
import johnengine.basic.renderer.components.VAO;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;
import johnengine.core.renderer.IDrawable;
import johnengine.core.renderer.shader.Shader;
import johnengine.core.renderer.shader.ShaderProgram;
import johnengine.core.renderer.shader.uniforms.UNIInteger;

public class CachedVAORenderBufferStrategy extends ARenderBufferStrategy {
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    
    private final TimedCache<Mesh, VAO> vaoCache;
    private final ShaderProgram shaderProgram;
    private final ConcurrentLinkedQueue<RenderBuffer> renderBufferQueue;
    private RenderBuffer currentRenderBuffer;
    private RenderBuffer lastRenderBuffer;
    private boolean isPrepared;

    public CachedVAORenderBufferStrategy() {
        super();
        this.vaoCache = new TimedCache<Mesh, VAO>(DEFAULT_EXPIRATION_TIME * 1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferQueue = new ConcurrentLinkedQueue<RenderBuffer>();
        this.currentRenderBuffer = null;
        this.lastRenderBuffer = null;
        this.isPrepared = false;
        
        this.addStrategoid(CModel.class, new StrategoidModel(this));
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
        
        this.currentRenderBuffer = new RenderBuffer();
        this.isPrepared = true;
    }
    
    @Override
    public void execute(IDrawable world) {
        if( !this.isPrepared )
        return;
        
        world.render(this.renderer);
        this.renderBufferQueue.add(this.currentRenderBuffer);
        this.currentRenderBuffer = new RenderBuffer();
    }
    
    @Override
    public void dispose() {
        this.shaderProgram.dispose();
    }
    
    @Override
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
                VAO vao = this.vaoCache.get(mesh);
                Mesh.Data meshData = mesh.getData();
                
                    // If a cache for the VAO of this mesh cannot be found, create it and
                    // store it
                if( vao == null )
                {
                    vao = new VAO();
                    VBOContainer vbos = meshData.getVBOs();
                    vao.addVBO(vbos.getVerticesVBO());
                    vao.addVBO(vbos.getTextureCoordinatesVBO());
                    vao.setIndicesVBO(vbos.getIndicesVBO());
                    vao.generate();
                    
                    this.vaoCache.cacheItem(mesh, vao);
                }
                
                    // Bind texture
                GL30.glActiveTexture(GL30.GL_TEXTURE0);
                unit.getTexture().bind();
                
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
}
