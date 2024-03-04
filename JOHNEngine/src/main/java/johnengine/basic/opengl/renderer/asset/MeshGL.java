package johnengine.basic.opengl.renderer.asset;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.basic.opengl.renderer.vao.AVBO;
import johnengine.basic.opengl.renderer.vao.VBOIndices;
import johnengine.basic.opengl.renderer.vao.VBOType;
import johnengine.basic.opengl.renderer.vao.VBOVector2f;
import johnengine.basic.opengl.renderer.vao.VBOVector3f;

public class MeshGL implements IGraphicsStrategyGL {
    
    /*********************** VBOContainer-class ***********************/
    
    public static class VBOContainer {
        private Map<VBOType, AVBO<?, ?>> vbos;
        private VBOIndices indicesVBO;
        
        public VBOContainer() {
            this.vbos = new HashMap<VBOType, AVBO<?, ?>>();
            this.indicesVBO = null;
        }
        
        
        boolean disposeAll() {
            for( Map.Entry<VBOType, AVBO<?, ?>> en : this.vbos.entrySet() )
            en.getValue().dispose();
            
            return true;
        }
        
        public void setVBO(VBOType key, AVBO<?, ?> vbo) {
            if( vbo instanceof VBOIndices )
            this.indicesVBO = (VBOIndices) vbo;
            else
            this.vbos.put(key, vbo);
        }
        
        public AVBO<?, ?> getVBO(VBOType key) {
            return this.vbos.get(key);
        }
        
        public VBOIndices getIndicesVBO() {
            return this.indicesVBO;
        }
    }
    
    
    /*********************** MeshGL-class ***********************/
    
    public static void generateDefault(RendererGL renderer) {
        MeshGL meshGraphics = new MeshGL(renderer, Mesh.DEFAULT_INSTANCE);
        meshGraphics.generate();
    }
    
    
    /*********************** Class body ***********************/
    
    private final RendererGL renderer;
    private Mesh mesh;
    private VBOContainer vbos;
    
    public MeshGL(RendererGL renderer, Mesh mesh) {
        this.mesh = mesh;
        this.renderer = renderer;
        this.vbos = null;
    }
    
    public MeshGL(RendererGL renderer) {
        this(renderer, null);
    }
    
    
    @Override
    public void loaded() {
        this.renderer.getGraphicsAssetProcessor().generateGraphics(this);
    }
    
    @Override
    public boolean generate() {
        Mesh.Data data = this.mesh.getUnsafe();
        
            // Generate vertices VBO
        VBOVector3f vboVertices = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboVertices.generate(data.getVertices());
        
            // Generate normals VBO
        VBOVector3f vboNormals = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboNormals.generate(data.getNormals());
        
            // Generate UVs VBO
        VBOVector2f vboUVs = new VBOVector2f(GL46.GL_ARRAY_BUFFER);
        vboUVs.generate(data.getUVs());
        
            // Generate indices VBO
        VBOIndices vboIndices = new VBOIndices();
        vboIndices.generate(data.getFaces());
        
            // Generate tangents VBO
        VBOVector3f vboTangents = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboTangents.generate(data.getTangents());

            // Generate tangents VBO
        VBOVector3f vboBitangents = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboBitangents.generate(data.getBitangents());
        
        VBOContainer vbos = new VBOContainer();
        vbos.setVBO(VBOType.VERTICES, vboVertices);
        vbos.setVBO(VBOType.NORMALS, vboNormals);
        vbos.setVBO(VBOType.UVS, vboUVs);
        vbos.setVBO(VBOType.INDICES, vboIndices);
        vbos.setVBO(VBOType.TANGENTS, vboTangents);
        vbos.setVBO(VBOType.BITANGENTS, vboBitangents);
        
        this.vbos = vbos;
        this.mesh.setGraphics(this);
        
        return true;
    }

    @Override
    public void deload() {
        this.renderer.getGraphicsAssetProcessor().disposeGraphics(this);
    }
    
    @Override
    public boolean dispose() {
        return this.vbos.disposeAll();
    }
    
    
    /*********************** GETTERS ***********************/
    
    public VBOContainer getVBOs() {
        return this.vbos;
    }
    
    public Mesh getMesh() {
        return this.mesh;
    }
}
