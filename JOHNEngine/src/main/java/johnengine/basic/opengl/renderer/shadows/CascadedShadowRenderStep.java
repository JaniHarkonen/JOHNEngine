package johnengine.basic.opengl.renderer.shadows;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

import johnengine.Defaults;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.cachedvao.RenderBuffer;
import johnengine.basic.opengl.renderer.cachedvao.RenderUnit;
import johnengine.basic.opengl.renderer.shadows.structs.SCascadedShadow;
import johnengine.basic.opengl.renderer.shadows.uniforms.UNICascadedShadow;
import johnengine.basic.opengl.renderer.uniforms.UNIInteger;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;
import johnengine.basic.opengl.renderer.uniforms.UniformArray;
import johnengine.basic.opengl.renderer.vao.VAO;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;

public class CascadedShadowRenderStep {

    private ShaderProgram shaderProgram;
    private VAOCache vaoCache;
    private String modelMatrixKey;
    private String projectionMatrixKey;
    private int depthMapLevelCount;
    private ShadowMapBuffer shadowMapBuffer;
    private CascadeShadow[] cascadeShadows;
    private UniformArray<SCascadedShadow, UNICascadedShadow> cascadedShadowUniforms;
    private UniformArray<Integer, UNIInteger> shadowSamplerUniforms;
    
    public CascadedShadowRenderStep(
        ShaderProgram shaderProgram,
        VAOCache vaoCache,
        String modelMatrixKey, 
        String projectionMatrixKey,
        UniformArray<SCascadedShadow, UNICascadedShadow> cascadedShadowUniforms,
        UniformArray<Integer, UNIInteger> shadowSamplerUniforms
    ) {
        this.shaderProgram = shaderProgram;
        this.vaoCache = vaoCache;
        this.modelMatrixKey = modelMatrixKey;
        this.projectionMatrixKey = projectionMatrixKey;
        this.depthMapLevelCount = Defaults.SHADOW_DEPTH_MAP_LEVEL_COUNT;
        this.shadowMapBuffer = null;
        this.cascadeShadows = null;
        this.cascadedShadowUniforms = cascadedShadowUniforms;
        this.shadowSamplerUniforms = shadowSamplerUniforms;
    }

    
    public void initialize() {
        DepthTexture[] depthTextures = new DepthTexture[this.depthMapLevelCount];
        for( int i = 0; i < depthTextures.length; i++ )
        {
            depthTextures[i] = new DepthTexture(
                Defaults.SHADOW_MAP_WIDTH, 
                Defaults.SHADOW_MAP_HEIGHT, 
                GL46.GL_DEPTH_COMPONENT
            );
        }
        
        this.shadowMapBuffer = new ShadowMapBuffer(depthTextures);
        this.shadowMapBuffer.generate();
        this.cascadeShadows = new CascadeShadow[this.depthMapLevelCount];
        
        for( int i = 0; i < this.depthMapLevelCount; i++ )
        this.cascadeShadows[i] = new CascadeShadow();
    }

    public void render(RenderBuffer renderBuffer) {
        CascadeShadow.updateShadows(
            this.cascadeShadows, 
            renderBuffer.getCameraMatrix(), 
            renderBuffer.getProjectionMatrix(),
            renderBuffer.getDirectionalLight().v3Direction,
            new Vector3f(0, -1, 0)
        );
        
        this.shadowMapBuffer.bind();
        GL46.glViewport(0, 0, Defaults.SHADOW_MAP_WIDTH, Defaults.SHADOW_MAP_HEIGHT);
        this.shaderProgram.bind();
        
        for( int i = 0; i < this.depthMapLevelCount; i++ )
        {
            int depthTextureHandle = this.shadowMapBuffer.getDepthTexture(i).getHandle();
            GL46.glFramebufferTexture2D(
                GL46.GL_FRAMEBUFFER, 
                GL46.GL_DEPTH_ATTACHMENT, 
                GL46.GL_TEXTURE_2D, 
                depthTextureHandle, 
                0
            );
            
            CascadeShadow shadow = this.cascadeShadows[i];
            ((UNIMatrix4f) this.shaderProgram.getUniform(this.projectionMatrixKey))
            .set(shadow.getProjectionMatrix());
            
            GL46.glClear(GL46.GL_DEPTH_BUFFER_BIT);
            
            for( RenderUnit unit : renderBuffer.getBuffer() )
            {
                ((UNIMatrix4f) this.shaderProgram.getUniform(this.modelMatrixKey))
                .set(unit.positionMatrix);
                
                VAO vao = this.vaoCache.fetchVAO(unit.meshGraphics);
                vao.bind();
                
                GL46.glDrawElements(
                    GL46.GL_TRIANGLES,
                    unit.meshData.getVertexCount() * 3,
                    GL46.GL_UNSIGNED_INT,
                    0
                );
            }
        }
        
        this.shaderProgram.unbind();
        this.shadowMapBuffer.unbind();
    }
    
    
    public void setUniforms(int firstSamplerIndex) {
        
            // Sets sampler and shadow uniforms
            // Also binds shadow map textures
        int s = this.depthMapLevelCount;
        for( int i = 0; i < s; i++ )
        {
            UNIInteger uShadowSampler = this.shadowSamplerUniforms.getArrayIndex(i);
            uShadowSampler.set(firstSamplerIndex + i);
            
            CascadeShadow shadow = this.cascadeShadows[i];
            UNICascadedShadow uCascadedShadow = this.cascadedShadowUniforms.getArrayIndex(i);
            uCascadedShadow.set(
                new SCascadedShadow(shadow.getProjectionMatrix(), shadow.getLength())
            );
        }
    }
    
    public void bindDepthTextures(int firstDepthTextureIndex) {
        for( int i = 0; i < this.depthMapLevelCount; i++ )
        {
            DepthTexture texture = this.shadowMapBuffer.getDepthTexture(i);
            GL46.glActiveTexture(firstDepthTextureIndex + i);
            texture.bind();
        }
    }
}
