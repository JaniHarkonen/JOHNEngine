package johnengine.basic.renderer.asset;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import johnengine.basic.assets.IMesh;
import johnengine.basic.assets.sceneobj.Material;

public class Mesh extends ARendererAsset<IMesh<?>, Mesh.Data> {    
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
    
    public static class Data {
        private Vector3f[] vertices;
        private Vector3f[] normals;
        private Vector3f[] tangents;
        private Vector3f[] bitangents;
        private Vector2f[] uvs;
        private Face[] faces;
        
        public Data(
            Vector3f[] vertices, 
            Vector3f[] normals, 
            Vector2f[] uvs, 
            Face[] faces,
            Vector3f[] tangents,
            Vector3f[] bitangents
        ) {
            this.vertices = vertices;
            this.normals = normals;
            this.tangents = tangents;
            this.bitangents = bitangents;
            this.uvs = uvs;
            this.faces = faces;
        }
        
        public Data() {
            this(null, null, null, null, null, null);
        }
        
        
        public Vector3f[] getVertices() {
            return this.vertices;
        }
        
        public Vector3f[] getNormals() {
            return this.normals;
        }
        
        public Vector3f[] getTangents() {
            return this.tangents;
        }
        
        public Vector3f[] getBitangents() {
            return this.bitangents;
        }
        
        public Vector2f[] getUVs() {
            return this.uvs;
        }
        
        public Face[] getFaces() {
            return this.faces;
        }

        public int getVertexCount() {
            return this.vertices.length;
        }
        
        public int getNormalCount() {
            return this.normals.length;
        }
        
        public int getUVCount() {
            return this.uvs.length;
        }
        
        public int getFaceCount() {
            return this.faces.length;
        }
    }
    
    
    /********************** Mesh-class **********************/
    
    public static Mesh DEFAULT_INSTANCE;
    
    static {
        DEFAULT_INSTANCE = new Mesh("default-mesh", true);
        DEFAULT_INSTANCE.data = new Mesh.Data(
                // Vertices
            new Vector3f[] {
                new Vector3f(-0.5f, 0.5f, -1.0f),        // top left
                new Vector3f(-0.5f, -0.5f, -1.0f),       // bottom left
                new Vector3f(0.5f, -0.5f, -1.0f),        // bottom right
                new Vector3f(0.5f, 0.5f, -1.0f)          // top right
            }, 
            new Vector3f[0],        // FIX THIS
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
            },
            new Vector3f[0],        // FIX THIS
            new Vector3f[0]         // FIX THIS
        );
    }
    
    public static void populateMeshWithAIMesh(Mesh dest, AIMesh src) {
        if( dest == null || src == null )
        return;
        
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
        dest.data = new Data(
            aiVectorBufferToVector3fArray(src.mVertices()),  // vertices
            aiVectorBufferToVector3fArray(src.mNormals()),   // normals
            uvs,                                             // UVs
            faces.toArray(new Face[faces.size()]),           // indices
            aiVectorBufferToVector3fArray(src.mTangents()),  // tangents
            aiVectorBufferToVector3fArray(src.mBitangents()) // bitangents
        );
    }
    
    private static Vector3f[] aiVectorBufferToVector3fArray(AIVector3D.Buffer src) {
        Vector3f[] result = new Vector3f[src.remaining()];
        
        int i = 0;
        while( src.remaining() > 0 )
        {
            AIVector3D aiVector = src.get();
            result[i] = new Vector3f(aiVector.x(), aiVector.y(), aiVector.z());
            
            i++;
        }
        
        return result;
    }
    
    
    /********************** Class body **********************/
    
    private Material material;
    
    public Mesh(String name, boolean isPersistent) {
        super(name, isPersistent);
        this.material = null;
    }
    
    public Mesh(String name) {
        this(name, false);
    }


    public void setMaterial(Material material) {
        this.material = material;
    }
    
    
    @Override
    public Mesh.Data getDefault() {
        return DEFAULT_INSTANCE.data;
    }
    
    @Override
    public IMesh<?> getDefaultGraphics() {
        return DEFAULT_INSTANCE.graphics;
    }
    
    public Material getMaterial() {
        return this.material;
    }
}
