package johnengine.basic.renderer.asset;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IMesh;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.renderer.vao.AVBO;
import johnengine.basic.renderer.vao.VBOIndices;
import johnengine.basic.renderer.vao.VBOType;
import johnengine.basic.renderer.vao.VBOVector2f;
import johnengine.basic.renderer.vao.VBOVector3f;

public class MeshGL implements IMesh<MeshGL.VBOContainer> {
    
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
    
    public static void generateDefault() {
        MeshGL meshGraphics = new MeshGL(Mesh.DEFAULT_INSTANCE);
        meshGraphics.generate();
    }
    
    
    /*********************** Class body ***********************/
    
    private final Mesh mesh;
    private VBOContainer vbos;
    
    public MeshGL(IRendererAsset mesh) {
        this.mesh = (Mesh) mesh;
        this.mesh.graphics = this;
        this.vbos = null;
    }
    
    public MeshGL() {
        this.mesh = null;
        this.vbos = null;
    }
    
    
    @Override
    public IGraphicsAsset<MeshGL.VBOContainer> createInstance(IRendererAsset mesh) {
        return new MeshGL(mesh);
    }
    
    @Override
    public boolean generate() {
        Mesh.Data data = this.mesh.getDataDirect();
        
            // Generate vertices VBO
        //VBOVertices vboVertices = new VBOVertices();
        //vboVertices.generate(data.getVertices());
        VBOVector3f vboVertices = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboVertices.generate(data.getVertices());
        
            // Generate normals VBO
        //VBONormals vboNormals = new VBONormals();
        //vboNormals.generate(data.getNormals());
        VBOVector3f vboNormals = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboNormals.generate(data.getNormals());
        
            // Generate UVs VBO
        //VBOTextureCoordinates vboUVs = new VBOTextureCoordinates();
        //vboUVs.generate(data.getUVs());
        VBOVector2f vboUVs = new VBOVector2f(GL46.GL_ARRAY_BUFFER);
        vboUVs.generate(data.getUVs());
        
            // Generate indices VBO
        //VBOIndices vboIndices = new VBOIndices();
        //vboIndices.generate(data.getFaces());
        VBOIndices vboIndices = new VBOIndices();
        vboIndices.generate(data.getFaces());
        
            // Generate tangents VBO
        //VBOTangents vboTangents = new VBOTangents();
        //vboTangents.generate(data.getTangents());
        VBOVector3f vboTangents = new VBOVector3f(GL46.GL_ARRAY_BUFFER);
        vboTangents.generate(data.getTangents());

            // Generate tangents VBO
        //VBOBitangents vboBitangents = new VBOBitangents();
        //vboBitangents.generate(data.getTangents());
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
        return true;
    }
    
    @Override
    public boolean dispose() {
        return this.vbos.disposeAll();
    }
    
    
    @Override
    public VBOContainer getData() {
        return this.vbos;
    }
    
    @Override
    public Mesh getMesh() {
        return this.mesh;
    }
}
