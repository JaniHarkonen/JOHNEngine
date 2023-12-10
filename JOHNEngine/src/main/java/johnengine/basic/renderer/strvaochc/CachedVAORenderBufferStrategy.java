package johnengine.basic.renderer.strvaochc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.CAttenuation;
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
import johnengine.basic.renderer.components.VAO;
import johnengine.basic.renderer.components.uniforms.UNIFloat;
import johnengine.basic.renderer.components.uniforms.UNIInteger;
import johnengine.basic.renderer.components.uniforms.UNIMatrix4f;
import johnengine.basic.renderer.components.uniforms.UNIVector3f;
import johnengine.basic.renderer.components.uniforms.UNIVector4f;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.ARenderBufferStrategy;
import johnengine.core.renderer.ARenderer;

public class CachedVAORenderBufferStrategy extends ARenderBufferStrategy {
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    public static final int MAX_POINT_LIGHT_COUNT = 5;
    
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
    
    private JAmbientLight ambientLight;
    private Map<JPointLight, Boolean> pointLights;

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
        UNIFloat ambientLightIntensity = new UNIFloat("ambientLightIntensity", "uAmbientLight.fIntensity");
        UNIVector3f ambientLightColor = new UNIVector3f("ambientLightColor", "uAmbientLight.c3Ambient");
        UNIVector4f materialDiffuseColor = new UNIVector4f("materialDiffuseColor", "uMaterial.c4Diffuse");
        UNIVector4f materialSpecularColor = new UNIVector4f("materialSpecularColor", "uMaterial.c4Specular");
        UNIFloat materialReflectance = new UNIFloat("materialReflectance", "uMaterial.fReflectance");
        
        this.shaderProgram
        .declareUniform(textureSampler)
        .declareUniform(projectionMatrix)
        .declareUniform(cameraMatrix)
        .declareUniform(modelMatrix)
        .declareUniform(ambientLightIntensity)
        .declareUniform(ambientLightColor)
        .declareUniform(materialDiffuseColor)
        .declareUniform(materialSpecularColor)
        .declareUniform(materialReflectance);
        
            // Declare point light uniforms
        for( int i = 0; i < MAX_POINT_LIGHT_COUNT; i++ )
        {
                // Prefix and identity prefix
            String pl = "pointLight";
            String plIndex = "uPointLight" + "[" + i + "]";
            
                // Light intensity
            this.shaderProgram.declareUniform(
                new UNIFloat(pl + "Intensity_" + i, 
                plIndex + ".fIntensity"
            ));
            
                // Light position
            this.shaderProgram.declareUniform(
                new UNIVector3f(pl + "Position_" + i, 
                plIndex + ".v3Position"
            ));
            
                // Light color
            this.shaderProgram.declareUniform(
                new UNIVector3f(pl + "Color_" + i,
                plIndex + ".c3Light"
            ));
            
                // Prefixes for attenuation (secondary prefixes)
            String plAtt = pl + "Attenuation";
            String plIndAtt = plIndex + ".attenuation";

                // Attenuation constant
            this.shaderProgram.declareUniform(
                new UNIFloat(plAtt + "Constant_" + i,
                plIndAtt + ".fConstant"
            ));
            
                // Attenuation linear
            this.shaderProgram.declareUniform(
                new UNIFloat(plAtt + "Linear_" + i,
                plIndAtt + ".fLinear"
            ));
            
                // Attenuation exponent
            this.shaderProgram.declareUniform(
                new UNIFloat(plAtt + "Exponent_" + i,
                plIndAtt + ".fExponent"
            ));
        }
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
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
            // Setup view matrix uniforms
        ((UNIMatrix4f) this.shaderProgram.getUniform("projectionMatrix")).set(this.projectionMatrix);
        ((UNIMatrix4f) this.shaderProgram.getUniform("cameraMatrix")).set(this.cameraMatrix);
        
            // Setup ambient light uniforms
        if( this.ambientLight != null )
        {
            ((UNIFloat) this.shaderProgram.getUniform("ambientLightIntensity")).set(this.ambientLight.getIntensity());
            ((UNIVector3f) this.shaderProgram.getUniform("ambientLightColor")).set(this.ambientLight.getColor());
        }
        else
        {
            ((UNIFloat) this.shaderProgram.getUniform("ambientLightIntensity")).set(JAmbientLight.DEFAULT_INTENSITY);
            ((UNIVector3f) this.shaderProgram.getUniform("ambientLightColor")).set(JAmbientLight.DEFAULT_COLOR);
        }
        
            // Setup point light uniforms
        int pointLightIndex = 0;
        for( Map.Entry<JPointLight, Boolean> en : this.pointLights.entrySet() )
        {
            JPointLight pointLight = en.getKey();
            ((UNIFloat) this.shaderProgram.getUniform("pointLightIntensity_" + pointLightIndex))
            .set(pointLight.getIntensity());
            
            ((UNIVector3f) this.shaderProgram.getUniform("pointLightPosition_" + pointLightIndex))
            .set(pointLight.getPosition().get());
            
            ((UNIVector3f) this.shaderProgram.getUniform("pointLightColor_" + pointLightIndex))
            .set(pointLight.getColor());
            
            CAttenuation attenuation = pointLight.getAttenuation();
            ((UNIFloat) this.shaderProgram.getUniform("pointLightAttenuationConstant_" + pointLightIndex))
            .set(attenuation.getConstant());
            
            ((UNIFloat) this.shaderProgram.getUniform("pointLightAttenuationLinear_" + pointLightIndex))
            .set(attenuation.getLinear());
            
            ((UNIFloat) this.shaderProgram.getUniform("pointLightAttenuationExponent_" + pointLightIndex))
            .set(attenuation.getExponent());
            pointLightIndex++;
            
            if( pointLightIndex >= MAX_POINT_LIGHT_COUNT )
            break;
        }
        
            // Fill the rest of the unused point light uniforms
        for( int i = pointLightIndex; i < MAX_POINT_LIGHT_COUNT; i++ )
        {
            ((UNIFloat) this.shaderProgram.getUniform("pointLightIntensity_" + pointLightIndex))
            .set(0.0f);
            
            ((UNIVector3f) this.shaderProgram.getUniform("pointLightPosition_" + pointLightIndex))
            .set(new Vector3f(0.0f));
            
            ((UNIVector3f) this.shaderProgram.getUniform("pointLightColor_" + pointLightIndex))
            .set(new Vector3f(0.0f));
            
            ((UNIFloat) this.shaderProgram.getUniform("pointLightAttenuationConstant_" + pointLightIndex))
            .set(0.0f);
            
            ((UNIFloat) this.shaderProgram.getUniform("pointLightAttenuationLinear_" + pointLightIndex))
            .set(0.0f);
            
            ((UNIFloat) this.shaderProgram.getUniform("pointLightAttenuationExponent_" + pointLightIndex))
            .set(0.0f);
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
                
                ((UNIVector4f) this.shaderProgram.getUniform("materialDiffuseColor")).set(material.getDiffuseColor());
                ((UNIVector4f) this.shaderProgram.getUniform("materialSpecularColor")).set(material.getSpecularColor());
                ((UNIFloat) this.shaderProgram.getUniform("materialReflectance")).set(material.getReflectance());
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
    
    public void setAmbientLight(JAmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }
    
    public void addPointLight(JPointLight pointLight) {
        if( this.pointLights.get(pointLight) == null )
        this.pointLights.put(pointLight, true);
    }
}
