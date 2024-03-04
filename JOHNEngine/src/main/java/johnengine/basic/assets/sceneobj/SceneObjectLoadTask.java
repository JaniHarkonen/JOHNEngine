package johnengine.basic.assets.sceneobj;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.core.assetmngr.asset.ALoadTask;

public class SceneObjectLoadTask extends ALoadTask {
    
    private class ExpectedMesh {
        public final Mesh mesh;
        public final IGraphicsStrategy graphicsStrategy;
        
        private ExpectedMesh(Mesh mesh, IGraphicsStrategy graphicsStrategy) {
            this.mesh = mesh;
            this.graphicsStrategy = graphicsStrategy;
        }
    }
    
    public static final int DEFAULT_IMPORT_FLAGS = 
        Assimp.aiProcess_JoinIdenticalVertices |
        Assimp.aiProcess_Triangulate |
        Assimp.aiProcess_FixInfacingNormals |
        Assimp.aiProcess_LimitBoneWeights |
        Assimp.aiProcess_CalcTangentSpace;
    
    private final List<ExpectedMesh> expectedMeshes;
    //private final List<Animation> expectedAnimations;
    
    private int importFlags;

    public SceneObjectLoadTask(String path) {
        super(path);
        this.expectedMeshes = new ArrayList<>();
        //this.expectedAnimations = new ArrayList<>();
        this.importFlags = DEFAULT_IMPORT_FLAGS;
    }
    
    public SceneObjectLoadTask() {
        this(null);
    }
    
    
    @Override
    protected void loadImpl() {
        AIScene scene = Assimp.aiImportFile(this.getPath(), this.importFlags);
        int s;
        
            // Extract meshes
        s = Math.min(this.expectedMeshes.size(), scene.mNumMeshes());
        for( int i = 0; i < s; i++ )
        {
            ExpectedMesh expectedMesh = this.expectedMeshes.get(i);
            AIMesh aiMesh = AIMesh.create(scene.mMeshes().get(i));
            Mesh.populateMeshWithAIMesh(expectedMesh.mesh, aiMesh);
            expectedMesh.graphicsStrategy.loaded();
        }
        
            // Extract animations
        s = scene.mNumAnimations();
        //for( int i = 0; i < s; i++ )
        //this.expectedAnimations.get(i).setData(AIAnimation.create(scene.mAnimations().get(i)));
        
        Assimp.aiReleaseImport(scene);
    }
    
    public void expectMesh(Mesh mesh, IGraphicsStrategy graphicsStrategy) {
        this.expectedMeshes.add(new ExpectedMesh(mesh, graphicsStrategy));
    }
    
    public void setImportFlags(int importFlags) {
        this.importFlags = importFlags;
    }
}
