package johnengine.basic.assets.mesh;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import johnengine.basic.assets.IGraphicsAsset;
import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.testing.DebugUtils;

public class Mesh implements IGraphicsAsset {
    
    /********************** Face-class **********************/
    
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
    
    
    /********************** Mesh-class **********************/
    
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
        
        for( Vector3f v : aiVectorBufferToVector3fArray(src.mTangents()) )
            DebugUtils.log("LOL", v.x, v.y, v.z);
        
            // Populate
        dest.info.setAsset(new MeshInfo.Data(
            aiVectorBufferToVector3fArray(src.mVertices()),  // vertices
            aiVectorBufferToVector3fArray(src.mNormals()),   // normals
            uvs,                                             // UVs
            faces.toArray(new Face[faces.size()]),           // indices
            aiVectorBufferToVector3fArray(src.mTangents()),  // tangents
            aiVectorBufferToVector3fArray(src.mBitangents()) // bitangents
        ));
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
    private IGraphicsStrategy graphicsStrategy;
    private MeshInfo info;
    private String name;
    
    public Mesh(String name, MeshInfo preloadedInfo, IGraphicsStrategy graphicsStrategy) {
        this.name = name;
        this.material = null;
        this.graphicsStrategy = graphicsStrategy;
        this.info = preloadedInfo;
    }
    
    public Mesh(String name) {
        this.name = name;
        this.material = null;
        this.graphicsStrategy = null;
        this.info = MeshInfo.DEFAULT_MESH_INFO;
    }

    
    @Override
    public void deload() {
        this.info.deload();
        this.graphicsStrategy.deload();
    }

    
    /********************** SETTERS **********************/
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    
    @Override
    public void setGraphicsStrategy(IGraphicsStrategy graphicsStrategy) {
        this.graphicsStrategy = graphicsStrategy;
    }
    
    
    /********************** GETTERS **********************/
    
    @Override
    public IGraphicsStrategy getGraphicsStrategy() {
        return this.graphicsStrategy;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public MeshInfo getInfo() {
        return this.info;
    }
}
