package johnengine.basic.assets.sceneobj;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.opengl.renderer.asset.Mesh;
import johnengine.core.assetmngr.asset.ALoadTask;
import johnengine.core.assetmngr.asset.IAsset;

public class SceneObjectLoadTask2 extends ALoadTask {
    
    public static final int DEFAULT_IMPORT_FLAGS = 
        Assimp.aiProcess_JoinIdenticalVertices |
        Assimp.aiProcess_Triangulate |
        Assimp.aiProcess_FixInfacingNormals |
        Assimp.aiProcess_LimitBoneWeights |
        Assimp.aiProcess_CalcTangentSpace;
    
    private final IGraphicsStrategy graphics;
    private final List<Mesh> expectedMeshes;
    //private final List<Animation> expectedAnimations;
    
    private int importFlags;
    //private ILoaderMonitor<IRendererAsset> monitor;

    public SceneObjectLoadTask2(IGraphicsStrategy graphics, String path) {
        super(path);
        this.graphics = graphics;
        this.expectedMeshes = new ArrayList<>();
        //this.expectedAnimations = new ArrayList<>();
        this.importFlags = DEFAULT_IMPORT_FLAGS;
        //this.monitor = null;
    }
    
    public SceneObjectLoadTask2(IGraphicsStrategy graphics) {
        this(graphics, null);
    }
    
    
    @Override
    protected void loadImpl() {
        AIScene scene = Assimp.aiImportFile(this.getPath(), this.importFlags);
        int s;
        
            // Extract meshes
        s = Math.min(this.expectedMeshes.size(), scene.mNumMeshes());
        for( int i = 0; i < s; i++ )
        {
            Mesh expectedMesh = this.expectedMeshes.get(i);
            AIMesh aiMesh = AIMesh.create(scene.mMeshes().get(i));
            Mesh.populateMeshWithAIMesh(expectedMesh, aiMesh);
            this.graphics.loaded();
            //if( this.monitor != null )
            //this.monitor.assetLoaded(expectedMesh);
        }
        
            // Extract animations
        s = scene.mNumAnimations();
        //for( int i = 0; i < s; i++ )
        //this.expectedAnimations.get(i).setData(AIAnimation.create(scene.mAnimations().get(i)));
        
        Assimp.aiReleaseImport(scene);
    }
    
    
    private <T extends IAsset> void expect(T asset, List<T> list) {
        list.add(asset);
    }
    
    public void expectMesh(Mesh mesh) {
        this.expect(mesh, this.expectedMeshes);
    }
    
    /*public void setMonitor(ILoaderMonitor<IRendererAsset> monitor) {
        this.monitor = monitor;
    }*/
    
    public void setImportFlags(int importFlags) {
        this.importFlags = importFlags;
    }
    
    /*
    public void expectAnimation(Animation mesh) {
        this.expect(mesh, this.expectedAnimations);
    }*/
}
