package johnengine.basic.renderer.asset;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import johnengine.basic.renderer.components.VBOIndices;
import johnengine.basic.renderer.components.VBOTextureCoordinates;
import johnengine.basic.renderer.components.VBOVertices;
import johnengine.core.renderer.ARenderer;

public class Mesh extends ARendererAsset<Mesh.Data> {
    
    public static class Face {
        public static final int INDICES_PER_FACE = 3;
        
        private final int[] indices;
        
        public Face(int[] indices) {
            this.indices = indices;
        }
        
        
        public int getIndex(int position) {
            return this.indices[position];
        }
    }
    
    public static class Data extends ARendererAsset.Data {
        private Vector3f[] vertices;
        private Vector2f[] uvs;
        private Face[] faces;
        private VBOContainer vbos;
        
        public Data(Vector3f[] vertices, Vector2f[] uvs, Face[] faces) {
            this.vertices = vertices;
            this.uvs = uvs;
            this.faces = faces;
            this.vbos = null;
        }
        
        public Data() {
            this(new Vector3f[0], new Vector2f[0], new Face[0]);
        }
        
        
        @Override
        public void generate() {
            Data data = this;
            
            VBOVertices vboVertices = new VBOVertices(0);
            vboVertices.generate(data.vertices);
            
            VBOTextureCoordinates vboUVs = new VBOTextureCoordinates(1);
            vboUVs.generate(data.uvs);
            
            VBOIndices vboIndices = new VBOIndices(2);
            vboIndices.generate(data.faces);
            
            data.vbos = new VBOContainer(vboVertices, vboUVs, vboIndices);
        }
        
        @Override
        public void dispose() {
            this.vbos.getVerticesVBO().delete();
            this.vbos.getTextureCoordinatesVBO().delete();
            this.vbos.getIndicesVBO().delete();
        }

        public int getVertexCount() {
            return this.vertices.length;
        }
        
        public int getUVCount() {
            return this.uvs.length;
        }
        
        public int getFaceCount() {
            return this.faces.length;
        }
        
        public VBOContainer getVBOs() {
            return this.vbos;
        }
    }
    
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
    
    
    /********************** Mesh-class **********************/
    
    public static Data DEFAULT_DATA = null;
    
    public static void generateDefault() {
        if( DEFAULT_DATA != null )
        return;
        
        DEFAULT_DATA = new Mesh.Data(
                // Vertices
            new Vector3f[] {
                new Vector3f(-0.5f, 0.5f, 0.0f),        // top left
                new Vector3f(-0.5f, -0.5f, 0.0f),       // bottom left
                new Vector3f(0.5f, -0.5f, 0.0f),        // bottom right
                new Vector3f(0.5f, 0.5f, 0.0f)          // top right
            }, 
                // UVs
            new Vector2f[] {
                new Vector2f(0.0f, 1.0f), 
                new Vector2f(0.0f, 0.0f), 
                new Vector2f(1.0f, 0.0f), 
                new Vector2f(1.0f, 1.0f),
            }, 
                // Faces
            new Mesh.Face[] {
                new Mesh.Face(new int[] {0, 1, 3}),
                new Mesh.Face(new int[] {3, 1, 2})
            }
        );
        
        DEFAULT_DATA.generate();
    }
    
    public static void populateMeshWithAIMesh(Mesh dest, AIMesh src) {
        if( dest == null || src == null )
        return;
        
            // Extract vertices
        AIVector3D.Buffer aiVertexBuffer = src.mVertices();
        List<Vector3f> vertexList = new ArrayList<>();
        while( aiVertexBuffer.remaining() > 0 )
        {
            AIVector3D vertex = aiVertexBuffer.get();
            vertexList.add(new Vector3f(vertex.x(), vertex.y(), vertex.z()));
        }
        
            // Extract UV-coordinates
        AIVector3D.Buffer aiUVBuffer = src.mTextureCoords(0);
        Vector2f[] uvs = new Vector2f[aiUVBuffer.remaining()];
        int pos = 0;
        while( aiUVBuffer.remaining() > 0 )
        {
            AIVector3D uv = aiUVBuffer.get();
            uvs[pos++] = new Vector2f(uv.x(), 1 - uv.y());
        }
        
            // Extract faces and their indices
        AIFace.Buffer aiFaceBuffer = src.mFaces();
        List<Face> faces = new ArrayList<>();
        int s = src.mNumFaces();
        for( int i = 0; i < s; i++ )
        {
            AIFace face = aiFaceBuffer.get(i);
            IntBuffer faceIndices = face.mIndices();
            
            int[] indices = new int[Face.INDICES_PER_FACE];
            pos = 0;    // This is defined earlier when extracting UVs
            while( faceIndices.remaining() > 0 )
            indices[pos++] = faceIndices.get();
            
            faces.add(new Face(indices));
        }
        
            // Populate
        dest.asset = new Data(
            vertexList.toArray(new Vector3f[vertexList.size()]),
            uvs,
            faces.toArray(new Face[faces.size()])
        );
        
        dest.loadingFinished();
    }
    
    /********************** Class body **********************/

    public Mesh(String name, boolean isPersistent, ARenderer renderer) {
        super(name, isPersistent, renderer);
    }
    
    public Mesh(String name) {
        this(name, false, null);
    }

    
    @Override
    public void generate() {
    /*    Data data = this.get();
        
        VBOVertices vboVertices = new VBOVertices(0);
        vboVertices.generate(data.vertices);
        
        VBOTextureCoordinates vboUVs = new VBOTextureCoordinates(1);
        vboUVs.generate(data.uvs);
        
        VBOIndices vboIndices = new VBOIndices(2);
        vboIndices.generate(data.faces);
        
        data.vbos = new VBOContainer(vboVertices, vboUVs, vboIndices);*/
    }
    
    @Override
    protected void deloadImpl() {
        if( this.asset == null )
        return;
        
        this.renderer.disposeAssetData(this.get());
    }
    
    @Override
    public void bind() {
        
    }
    
    @Override
    public void unbind() {
        
    }

    
    @Override
    public Mesh.Data getDefault() {
        return DEFAULT_DATA;
    }
    
    
    public Data getData() {
        return this.get();
    }
}
