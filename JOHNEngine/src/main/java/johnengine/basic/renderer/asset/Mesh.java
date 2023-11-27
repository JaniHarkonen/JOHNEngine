package johnengine.basic.renderer.asset;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.opengl.GL30;

import johnengine.basic.renderer.Renderer3D;
import johnengine.basic.renderer.components.VBOIndices;
import johnengine.basic.renderer.components.VBOTextureCoordinates;
import johnengine.basic.renderer.components.VBOVertices;
import johnengine.basic.renderer.components.VertexArrayObject;
import johnengine.core.renderer.ARenderer;

public class Mesh extends ARendererAsset<VertexArrayObject> {
    
    public static class Face {
        public static final int INDICES_PER_FACE = 3;
        
        private final int[] indices;
        
        public Face(int[] indices) {
            this.indices = indices;
        }
        
        
        public int get(int position) {
            return this.indices[position];
        }
    }
    
    
    /********************** Mesh-class **********************/
    
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
        dest.vertices = vertexList.toArray(new Vector3f[vertexList.size()]);
        dest.uvs = uvs;
        dest.faces = faces.toArray(new Face[faces.size()]);
    }
    
    /********************** Class body **********************/
    
    private Vector3f[] vertices;
    private Vector2f[] uvs;
    private Face[] faces;

    public Mesh(String name, boolean isPersistent) {
        super(name, isPersistent);
        /*this.vertices = new Vector3f[0];
        this.uvs = new Vector2f[0];
        this.faces = new Face[0];*/
        
        this.vertices = new Vector3f[] {
            new Vector3f(-0.5f, 0.5f, 0.0f),
            new Vector3f(-0.5f, -0.5f, 0.0f),
            new Vector3f(0.5f, -0.5f, 0.0f)
        };
        
        this.uvs = new Vector2f[] {
            new Vector2f(0.0f, 0.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(0.0f, 1.0f)
        };
        
        this.faces = new Face[] {
            new Face(new int[] {0, 1, 2})
        };
    }
    
    public Mesh(String name) {
        this(name, false);
    }

    
    @Override
    protected void deloadImpl() {
        if( this.asset != null )
        this.asset.free();
    }

    
    @Override
    public VertexArrayObject getDefault() {
        return Renderer3D.DEFAULT_VAO;
    }
    

    @Override
    public void render(ARenderer renderer) {
        if( this.asset == null )
        return;
        
        VertexArrayObject vao = this.get();
        
        if( !vao.wasGenerated() )
        {
            vao.addVBO(new VBOVertices(this.vertices));
            vao.addVBO(new VBOTextureCoordinates(this.uvs));
            vao.addVBO(new VBOIndices(this.faces));
            vao.generate();
        }
        
        vao.bind();
        //DebugUtils.log(this, "vertices: " + this.getVertexCount() + " | uvs: " + this.getUVCount() + " | faces: " + this.getFaceCount());
        GL30.glDrawElements(GL30.GL_TRIANGLES, this.getVertexCount(), GL30.GL_UNSIGNED_INT, 0);
        vao.unbind();
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
}
