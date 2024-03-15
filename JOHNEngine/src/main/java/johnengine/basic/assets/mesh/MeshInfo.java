package johnengine.basic.assets.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;

import johnengine.Defaults;
import johnengine.core.assetmngr.asset.AAsset;

public class MeshInfo extends AAsset<MeshInfo.Data> {

    
    /********************** Data-class **********************/
    
    public static class Data {
        
        private static class State {
            private Vector3f[] vertices;
            private Vector3f[] normals;
            private Vector3f[] tangents;
            private Vector3f[] bitangents;
            private Vector2f[] uvs;
            private Mesh.Face[] faces;
            
            private State(
                Vector3f[] vertices, 
                Vector3f[] normals, 
                Vector2f[] uvs, 
                Mesh.Face[] faces,
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
        }
        
        
        private State state;
        
        public Data(
            Vector3f[] vertices, 
            Vector3f[] normals, 
            Vector2f[] uvs, 
            Mesh.Face[] faces,
            Vector3f[] tangents,
            Vector3f[] bitangents
        ) {
            State state = new State(
                vertices, 
                normals, 
                uvs, 
                faces, 
                tangents, 
                bitangents
            );
           
            this.state = state;
        }
        
        public Data() {
            this(null, null, null, null, null, null);
        }
        
        
        public Vector3f[] getVertices() {
            return this.state.vertices;
        }
        
        public Vector3f[] getNormals() {
            return this.state.normals;
        }
        
        public Vector3f[] getTangents() {
            return this.state.tangents;
        }
        
        public Vector3f[] getBitangents() {
            return this.state.bitangents;
        }
        
        public Vector2f[] getUVs() {
            return this.state.uvs;
        }
        
        public Mesh.Face[] getFaces() {
            return this.state.faces;
        }

        public int getVertexCount() {
            return this.state.vertices.length;
        }
        
        public int getNormalCount() {
            return this.state.normals.length;
        }
        
        public int getUVCount() {
            return this.state.uvs.length;
        }
        
        public int getFaceCount() {
            return this.state.faces.length;
        }
    }

    
    /********************** MeshInfo-class **********************/
    
    public static void generateDefaults() {
        MeshInfo.Data.State dataState = new MeshInfo.Data.State(
                // Vertices
            new Vector3f[] {
                new Vector3f(-0.5f, 0.5f, -1.0f),        // top left
                new Vector3f(-0.5f, -0.5f, -1.0f),       // bottom left
                new Vector3f(0.5f, -0.5f, -1.0f),        // bottom right
                new Vector3f(0.5f, 0.5f, -1.0f)          // top right
            }, 
                // Normals
            new Vector3f[] {
                    // first polygon
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                
                    // second polygon
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f)
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
            },
                // Tangents
            new Vector3f[] {
                    // first polygon
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                
                    // second polygon
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f)
            },
                // Bitangents
            new Vector3f[] {
                    // first polygon
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                
                    // second polygon
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f)
            }
        );
        
        Defaults.DEFAULT_MESHINFO_DATA.state = dataState;
    }
    

    public MeshInfo(String name, boolean isPersistent, MeshInfo.Data preloadedAsset) {
        super(name, isPersistent, preloadedAsset);
    }
    
    public MeshInfo(String name) {
        super(name);
    }
    
    
    @Override
    public MeshInfo.Data getDefaultAsset() {
        return Defaults.DEFAULT_MESHINFO_DATA;
    }
    
    @Override
    protected void deloadImpl() {
        
    }
}
