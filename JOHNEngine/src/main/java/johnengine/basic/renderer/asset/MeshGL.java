package johnengine.basic.renderer.asset;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IMesh;
import johnengine.basic.assets.IRenderAsset;
import johnengine.basic.renderer.components.VBOIndices;
import johnengine.basic.renderer.components.VBOTextureCoordinates;
import johnengine.basic.renderer.components.VBOVertices;

public class MeshGL implements IMesh<MeshGL.VBOContainer> {
    
    public static class VBOContainer {
        private VBOVertices vboVertices;
        private VBOTextureCoordinates vboUVs;
        private VBOIndices vboIndices;
        
        public VBOContainer(
            VBOVertices vboVertices, 
            VBOTextureCoordinates vboUVs, 
            VBOIndices vboIndices
        ) {
            this.vboVertices = vboVertices;
            this.vboUVs = vboUVs;
            this.vboIndices = vboIndices;
        }
        
        
        public VBOVertices getVerticesVBO() {
            return this.vboVertices;
        }
        
        public VBOTextureCoordinates getTextureCoordinatesVBO() {
            return this.vboUVs;
        }
        
        public VBOIndices getIndicesVBO() {
            return this.vboIndices;
        }
    }
    
    
    /*********************** MeshGL-class ***********************/
    
    public static void generateDefault() {
        MeshGL meshGraphics = new MeshGL(Mesh.DEFAULT_INSTANCE);
        meshGraphics.generate();
    }
    
    private final Mesh mesh;
    private VBOContainer vbos;
    
    public MeshGL(IRenderAsset mesh) {
        this.mesh = (Mesh) mesh;
        this.mesh.graphics = this;
        this.vbos = null;
    }
    
    public MeshGL() {
        this.mesh = null;
        this.vbos = null;
    }
    
    
    @Override
    public IGraphicsAsset<MeshGL.VBOContainer> createInstance(IRenderAsset mesh) {
        return new MeshGL(mesh);
    }
    
    @Override
    public void generate() {
        Mesh.Data data = this.mesh.getDataDirect();
        
        VBOVertices vboVertices = new VBOVertices();
        vboVertices.generate(data.getVertices());
        
        VBOTextureCoordinates vboUVs = new VBOTextureCoordinates();
        vboUVs.generate(data.getUVs());
        
        VBOIndices vboIndices = new VBOIndices();
        vboIndices.generate(data.getFaces());
        
        this.vbos = new VBOContainer(vboVertices, vboUVs, vboIndices);
    }
    
    @Override
    public void dispose() {
        //this.mesh.graphics = null;
        this.vbos.getVerticesVBO().delete();
        this.vbos.getTextureCoordinatesVBO().delete();
        this.vbos.getIndicesVBO().delete();
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
