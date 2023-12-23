package johnengine.basic.renderer.strvaochc;

import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.renderer.ShaderProgram;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.MeshGL.VBOContainer;
import johnengine.basic.renderer.asset.Shader;
import johnengine.basic.renderer.asset.Texture;
import johnengine.basic.renderer.asset.TextureGL;
import johnengine.basic.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.renderer.strvaochc.structs.SDirectionalLight;
import johnengine.basic.renderer.strvaochc.structs.SMaterial;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;
import johnengine.basic.renderer.strvaochc.structs.SSpotLight;
import johnengine.basic.renderer.strvaochc.uniforms.UNIAmbientLight;
import johnengine.basic.renderer.strvaochc.uniforms.UNIDirectionalLight;
import johnengine.basic.renderer.strvaochc.uniforms.UNIMaterial;
import johnengine.basic.renderer.strvaochc.uniforms.UNIPointLight;
import johnengine.basic.renderer.strvaochc.uniforms.UNISpotLight;
import johnengine.basic.renderer.uniforms.UNIInteger;
import johnengine.basic.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.renderer.uniforms.UniformArray;
import johnengine.basic.renderer.vertex.VAO;
import johnengine.core.IRenderable;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.IRenderBufferStrategoid;
import johnengine.core.renderer.IRenderStrategy;
import johnengine.core.renderer.IRenderer;
import johnengine.core.renderer.RenderStrategoidManager;

public class CachedVAORenderStrategy implements 
    IRenderStrategy,
    IHasRenderBuffer
{
    public static final int DEFAULT_EXPIRATION_TIME = 10;   // in seconds
    public static final int MAX_POINT_LIGHT_COUNT = 5;
    public static final int MAX_SPOT_LIGHT_COUNT = 5;
    
    private final IRenderer renderer;
    private final TimedCache<MeshGL, VAO> vaoCache;
    private final ShaderProgram shaderProgram;
    private RenderBufferManager renderBufferManager;
    private RenderStrategoidManager strategoidManager;
    
    public CachedVAORenderStrategy(IRenderer renderer) {
        this.renderer = renderer;
        this.vaoCache = new TimedCache<>(DEFAULT_EXPIRATION_TIME * 1000);
        this.shaderProgram = new ShaderProgram();
        this.renderBufferManager = new RenderBufferManager();
        this.strategoidManager = new RenderStrategoidManager();
        
        this.strategoidManager.addStrategoid(CModel.class, new StrategoidModel(this));
        this.strategoidManager.addStrategoid(JCamera.class, new StrategoidCamera(this));
        this.strategoidManager.addStrategoid(JAmbientLight.class, new StrategoidAmbientLight(this));
        this.strategoidManager.addStrategoid(JPointLight.class, new StrategoidPointLight(this));
        this.strategoidManager.addStrategoid(JDirectionalLight.class, new StrategoidDirectionalLight(this));
        this.strategoidManager.addStrategoid(JSpotLight.class, new StrategoidSpotLight(this));
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
        UNIDirectionalLight directionalLight = new UNIDirectionalLight("directionalLight", "uDirectionalLight");
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
        .declareUniform(projectionMatrix)
        .declareUniform(cameraMatrix)
        .declareUniform(modelMatrix)
        .declareUniform(ambientLight)
        .declareUniform(material)
        .declareUniform(pointLight)
        .declareUniform(directionalLight)
        .declareUniform(spotLight);
    }
    
    @Override
    public void newBuffer() {
        this.renderBufferManager.newBuffer();
    }
    
    @Override
    public boolean executeStrategoid(IRenderable target) {
        IRenderBufferStrategoid<IRenderable> strategoid = this.strategoidManager.getStrategoid(target.getClass());
        
        if( strategoid == null )
        return false;
        
        strategoid.execute(target);
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public void preRender(RenderBuffer renderBuffer) {
        this.shaderProgram.bind();
        this.shaderProgram.getUniform("textureSampler").set();
        
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
            // Pre-render
        RenderBuffer renderBuffer = this.renderBufferManager.poll();
        this.preRender(renderBuffer);
        
        UNIMatrix4f modelMatrix = (UNIMatrix4f) this.shaderProgram.getUniform("modelMatrix");
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
        
            // Post-render
        this.postRender();
    }
    
    public void postRender() {
        GL30.glBindVertexArray(0);
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
    
    @Override
    public void addRenderUnit(RenderUnit unit) {
        this.renderBufferManager.getCurrentBuffer().addRenderUnit(unit);
    }
    
    @Override
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.renderBufferManager.getCurrentBuffer().setProjectionMatrix(projectionMatrix);
    }
    
    @Override
    public void setCameraMatrix(Matrix4f cameraMatrix) {
        this.renderBufferManager.getCurrentBuffer().setCameraMatrix(cameraMatrix);
    }
    
    @Override
    public void setAmbientLight(SAmbientLight ambientLight) {
        this.renderBufferManager.getCurrentBuffer().setAmbientLight(ambientLight);
    }
    
    @Override
    public void setDirectionalLight(SDirectionalLight directionalLight) {
        this.renderBufferManager.getCurrentBuffer().setDirectionalLight(directionalLight);
    }
    
    @Override
    public void addPointLight(JPointLight pointLight, SPointLight pointLightStruct) {
        this.renderBufferManager.getCurrentBuffer().addPointLight(pointLight, pointLightStruct);
    }
    
    @Override
    public void addSpotLight(JSpotLight spotLight, SSpotLight spotLightStruct) {
        this.renderBufferManager.getCurrentBuffer().addSpotLight(spotLight, spotLightStruct);
    }
    
    @Override
    public SPointLight getPointLightStruct(JPointLight pointLight) {
        return this.renderBufferManager.getCurrentBuffer().getPointLightStruct(pointLight);
    }

    @Override
    public IRenderer getRenderer() {
        return this.renderer;
    }
    
    @Override
    public boolean canRender() {
        return (this.renderBufferManager.peekNext() != null);
    }
}
