package johnengine.basic.opengl.renderer.asset;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL46;

import johnengine.Defaults;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.mesh.MeshInfo;
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.basic.opengl.renderer.vao.AVBO;
import johnengine.basic.opengl.renderer.vao.VBOIndices;
import johnengine.basic.opengl.renderer.vao.VBOType;
import johnengine.basic.opengl.renderer.vao.VBOVector2f;
import johnengine.basic.opengl.renderer.vao.VBOVector3f;

public class MeshGraphicsGL extends AGraphicsStrategyGL<MeshGraphicsGL.VBOContainer> {
    
    /*********************** VBOContainer-class ***********************/
    
    public static class VBOContainer {
        
        private static class State {
            private Map<VBOType, AVBO<?, ?>> vbos;
            private VBOIndices indicesVBO;
            
            private State() {
                this.vbos = new HashMap<VBOType, AVBO<?, ?>>();
                this.indicesVBO = null;
            }
        }
        
        
        private State state;
        private boolean isPersistent;
        
        public VBOContainer(boolean isPersistent) {
            this.state = new State();
            this.isPersistent = false;
        }
        
        public VBOContainer() {
            this(false);
        }
        
        
        boolean disposeAll() {
            if( this.isPersistent )
            return false;
            
            for( Map.Entry<VBOType, AVBO<?, ?>> en : this.state.vbos.entrySet() )
            en.getValue().dispose();
            
            return true;
        }
        
        public void setVBO(VBOType key, AVBO<?, ?> vbo) {
            if( vbo instanceof VBOIndices )
            this.state.indicesVBO = (VBOIndices) vbo;
            else
            this.state.vbos.put(key, vbo);
            
            vbo.setType(key);
        }
        
        public void setPersistent(boolean isPersistent) {
            this.isPersistent = isPersistent;
        }
        
        public AVBO<?, ?> getVBO(VBOType key) {
            return this.state.vbos.get(key);
        }
        
        public VBOIndices getIndicesVBO() {
            return this.state.indicesVBO;
        }
    }
    
    
    /*********************** MeshGraphicsGL-class ***********************/
    
    public static VBOContainer DEFAULT_VBOS = new VBOContainer(true);
    
    public static void generateDefault(RendererGL renderer) {
        VBOContainer defaultVBOContainer = generateVBOs(Defaults.DEFAULT_MESHINFO_DATA);
        MeshGraphicsGL.DEFAULT_VBOS.state = defaultVBOContainer.state;
    }
    
    private static VBOContainer generateVBOs(MeshInfo.Data data) {
        
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
        
        return vbos;
    }
    
    
    /*********************** MeshGraphicsGL-class ***********************/
    
    private Mesh mesh;
    
    public MeshGraphicsGL(RendererGL renderer, Mesh mesh, boolean isPersistent) {
        super(renderer, isPersistent);
        this.mesh = mesh;
    }
    
    public MeshGraphicsGL(RendererGL renderer) {
        this(renderer, null, false);
    }
    
    
    @Override
    public void loaded() {
        this.renderer.getGraphicsAssetProcessor().generateGraphics(this);
    }
    
    @Override
    public boolean generate() {
        MeshInfo.Data data = this.mesh.getInfo().getAsset().get();
        this.graphics.set(generateVBOs(data));
        this.mesh.setGraphicsStrategy(this);
        
        return true;
    }
    
    @Override
    protected void deloadImpl() {
        this.renderer.getGraphicsAssetProcessor().disposeGraphics(this);
    }
    
    @Override
    public boolean dispose() {
        return this.graphics.get().disposeAll();
    }
    
    
    /*********************** SETTERS ***********************/
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
    
    
    /*********************** GETTERS ***********************/
    
    public Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public VBOContainer getDefaultGraphics() {
        return MeshGraphicsGL.DEFAULT_VBOS;
    }
}
