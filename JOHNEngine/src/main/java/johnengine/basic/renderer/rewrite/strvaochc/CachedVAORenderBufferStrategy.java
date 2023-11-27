package johnengine.basic.renderer.rewrite.strvaochc;

import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import johnengine.basic.game.rewrite.CModel;
import johnengine.basic.renderer.rewrite.asset.Mesh;
import johnengine.basic.renderer.rewrite.asset.Mesh.VBOContainer;
import johnengine.basic.renderer.rewrite.asset.Texture;
import johnengine.basic.renderer.rewrite.components.VAO;
import johnengine.core.cache.TimedCache;
import johnengine.core.renderer.rewrite.ARenderBufferStrategy;
import johnengine.core.renderer.rewrite.ARenderer;
import johnengine.core.renderer.rewrite.shader.ShaderProgram;
import johnengine.core.renderer.rewrite.shader.uniforms.UNIInteger;
import johnengine.core.renderer.rewrite.shader.uniforms.UNIMatrix4f;

public class CachedVAORenderBufferStrategy extends ARenderBufferStrategy {
    
    public static class RenderUnit {
        private Mesh mesh;
        private Texture texture;
        private Vector3f position;
        
        public RenderUnit(Mesh mesh, Texture texture, Vector3f position) {
            this.mesh = mesh;
            this.texture = texture;
            this.position = position;
        }
        
        
        Mesh getMesh() {
            return this.mesh;
        }
        
        Texture getTexture() {
            return this.texture;
        }
        
        Vector3f getPosition() {
            return this.position;
        }
    }
    
    
    /************************* CachedVAORenderBufferStrategy-class *************************/
    
    public static final int DEFAULT_EXPIRATION_TIME = 10;
    
    private final TimedCache<Mesh, VAO> vaoCache;
    private final ShaderProgram shaderProgram;
    private List<RenderUnit> renderBuffer;

    public CachedVAORenderBufferStrategy() {
        super();
        this.vaoCache = new TimedCache<Mesh, VAO>(DEFAULT_EXPIRATION_TIME * 1000);
        this.shaderProgram = new ShaderProgram();
        this.addStrategoid(CModel.class, new StrategoidModel(this));
    }
    
    
    @Override
    public void prepare() {
        UNIMatrix4f cameraOrientationMatrix = new UNIMatrix4f("cameraOrientationMatrix");
        UNIMatrix4f cameraProjectionMatrix = new UNIMatrix4f("cameraProjectionMatrix");
        UNIInteger textureSampler = new UNIInteger("textureSampler");
        
        this.shaderProgram
        .declareUniform(cameraOrientationMatrix)
        .declareUniform(cameraProjectionMatrix)
        .declareUniform(textureSampler);
    }
    
    @Override
    public void dispose() {
        this.shaderProgram.dispose();
    }
    
    @Override
    public void render(ARenderer renderer) {
        
            // Rendering pass
        this.shaderProgram.getUniform("textureSampler").set();
        
        for( RenderUnit unit : this.renderBuffer )
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
                
                this.vaoCache.cacheItem(mesh, vao);
            }
            
                // Bind texture
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            unit.getTexture().bind();
            
                // Bind mesh and draw
            GL30.glBindVertexArray(vao.getHandle());
            GL30.glDrawElements(GL30.GL_TRIANGLES, meshData.getVertexCount() * 3, GL30.GL_UNSIGNED_INT, 0);
        }
        
        GL30.glBindVertexArray(0);
        
            // Update cached VAOs and remove those that have been unused
            // for too long
        this.vaoCache.update();
    }
    
    
    public void addRenderUnit(RenderUnit unit) {
        this.renderBuffer.add(unit);
    }
}
