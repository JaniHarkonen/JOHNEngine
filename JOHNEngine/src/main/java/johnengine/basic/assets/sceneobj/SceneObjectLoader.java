package johnengine.basic.assets.sceneobj;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import johnengine.basic.assets.IRendererAsset;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.core.assetmngr.asset.AAssetLoader;
import johnengine.core.assetmngr.asset.IAsset;
import johnengine.core.assetmngr.asset.ILoaderMonitor;

public class SceneObjectLoader extends AAssetLoader {
    
    public static final int DEFAULT_IMPORT_FLAGS = 
        Assimp.aiProcess_JoinIdenticalVertices |
        Assimp.aiProcess_Triangulate |
        Assimp.aiProcess_FixInfacingNormals |
        Assimp.aiProcess_LimitBoneWeights;
    
    private final List<Mesh> expectedMeshes;
    //private final List<Material> expectedMaterials;
    //private final List<Animation> expectedAnimations;
    
    private int importFlags;
    private ILoaderMonitor<IRendererAsset> monitor;

    public SceneObjectLoader(String path) {
        super(path);
        this.expectedMeshes = new ArrayList<>();
        //this.expectedMaterials = new ArrayList<>();
        //this.expectedAnimations = new ArrayList<>();
        this.importFlags = DEFAULT_IMPORT_FLAGS;
        this.monitor = null;
    }
    
    public SceneObjectLoader() {
        this(null);
    }
    
    
    @Override
    protected void loadImpl() {
        AIScene scene = Assimp.aiImportFile(this.getPath(), this.importFlags);
        int s;
        
            // Extract meshes
        s = scene.mNumMeshes();
        for( int i = 0; i < s; i++ )
        {
            Mesh expectedMesh = this.expectedMeshes.get(i);
            AIMesh aiMesh = AIMesh.create(scene.mMeshes().get(i));
            Mesh.populateMeshWithAIMesh(expectedMesh, aiMesh);
            
            if( this.monitor != null )
            this.monitor.assetLoaded(expectedMesh);
        }
        
            // Extract materials
        //for( int i = 0; i < s; i++ )
        //this.expectedMaterials.get(i).setData(AIMaterial.create(scene.mMaterials().get(i)));
        
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
    
    public void setMonitor(ILoaderMonitor<IRendererAsset> monitor) {
        this.monitor = monitor;
    }
    
    /*public void expectMaterial(Material mesh) {
        this.expect(mesh, this.expectedMaterials);
    }
    
    public void expectAnimation(Animation mesh) {
        this.expect(mesh, this.expectedAnimations);
    }*/
}
