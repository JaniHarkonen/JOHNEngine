package johnengine.basic.renderer.asset;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IMesh;
import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.renderer.vertex.VBOBitangents;
import johnengine.basic.renderer.vertex.VBOIndices;
import johnengine.basic.renderer.vertex.VBONormals;
import johnengine.basic.renderer.vertex.VBOTangents;
import johnengine.basic.renderer.vertex.VBOTextureCoordinates;
import johnengine.basic.renderer.vertex.VBOVertices;

public class MeshGL implements IMesh<MeshGL.VBOContainer> {
    
    public static class VBOContainer {
        private VBOVertices vboVertices;
        private VBONormals vboNormals;
        private VBOTextureCoordinates vboUVs;
        private VBOIndices vboIndices;
        private VBOTangents vboTangents;
        private VBOBitangents vboBitangents;
        
        public VBOContainer(
            VBOVertices vboVertices,
            VBONormals vboNormals,
            VBOTextureCoordinates vboUVs, 
            VBOIndices vboIndices,
            VBOTangents vboTangents,
            VBOBitangents vboBitangents
        ) {
            this.vboVertices = vboVertices;
            this.vboNormals = vboNormals;
            this.vboUVs = vboUVs;
            this.vboIndices = vboIndices;
            this.vboTangents = vboTangents;
            this.vboBitangents = vboBitangents;
        }
        
        
        public VBOVertices getVerticesVBO() {
            return this.vboVertices;
        }
        
        public VBONormals getNormalsVBO() {
            return this.vboNormals;
        }
        
        public VBOTextureCoordinates getTextureCoordinatesVBO() {
            return this.vboUVs;
        }
        
        public VBOIndices getIndicesVBO() {
            return this.vboIndices;
        }
        
        public VBOTangents getTangentsVBO() {
            return this.vboTangents;
        }
        
        public VBOBitangents getBitangentsVBO() {
            return this.vboBitangents;
        }
    }
    
    
    /*********************** MeshGL-class ***********************/
    
    public static void generateDefault() {
        MeshGL meshGraphics = new MeshGL(Mesh.DEFAULT_INSTANCE);
        meshGraphics.generate();
    }
    
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
        VBOVertices vboVertices = new VBOVertices();
        vboVertices.generate(data.getVertices());
        
            // Generate normals VBO
        VBONormals vboNormals = new VBONormals();
        vboNormals.generate(data.getNormals());
        
            // Generate UVs VBO
        VBOTextureCoordinates vboUVs = new VBOTextureCoordinates();
        vboUVs.generate(data.getUVs());
        
            // Generate indices VBO
        VBOIndices vboIndices = new VBOIndices();
        vboIndices.generate(data.getFaces());
        
            // Generate tangents VBO
        VBOTangents vboTangents = new VBOTangents();
        vboTangents.generate(data.getTangents());

            // Generate tangents VBO
        VBOBitangents vboBitangents = new VBOBitangents();
        vboBitangents.generate(data.getTangents());
        
        this.vbos = new VBOContainer(
            vboVertices, 
            vboNormals, 
            vboUVs, 
            vboIndices, 
            vboTangents, 
            vboBitangents
        );
        
        return true;
    }
    
    @Override
    public boolean dispose() {
        //this.mesh.graphics = null;
        this.vbos.getVerticesVBO().dispose();
        this.vbos.getNormalsVBO().dispose();
        this.vbos.getTextureCoordinatesVBO().dispose();
        this.vbos.getIndicesVBO().dispose();
        this.vbos.getTangentsVBO().dispose();
        this.vbos.getBitangentsVBO().dispose();
        
        return true;
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
