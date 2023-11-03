/*package johnengine.basic.assets;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.AIAnimation;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import johnengine.basic.renderer.asset.Animation;
import johnengine.basic.renderer.asset.Material;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.core.assetmngr.asset.AAsset;

public class SceneObjectAsset extends AAsset<AIScene> {
    
    public static final int DEFAULT_IMPORT_FLAGS = 
        Assimp.aiProcess_JoinIdenticalVertices |
        Assimp.aiProcess_Triangulate |
        Assimp.aiProcess_FixInfacingNormals |
        Assimp.aiProcess_LimitBoneWeights;
    
    private int importFlags;
    private List<Animation> animations;
    private List<Material> materials;
    private List<Mesh> meshes;
    
    public SceneObjectAsset(String name, String relativePath, boolean isPersistent, AIScene preloadedAsset) {
        super(name, relativePath, isPersistent, preloadedAsset);
        this.animations = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.meshes = new ArrayList<>();
        this.importFlags = DEFAULT_IMPORT_FLAGS;
    }
    
    public SceneObjectAsset(String name, String relativePath) {
        this(name, relativePath, false, null);
    }
    

    @Override
    protected void loadImpl() {
       this.asset = Assimp.aiImportFile(this.getPath(), this.importFlags);
       int s;
       
           // Extract animations
       s = this.asset.mNumAnimations();
       for( int i = 0; i < s; i++ )
       this.animations.get(i).setAsset(AIAnimation.create(this.asset.mAnimations().get(i)));
   
           // Extract materials
       s = this.asset.mNumMaterials();
       for( int i = 0; i < s; i++ )
       this.materials.get(i).setAsset(AIMaterial.create(this.asset.mMaterials().get(i)));
       
           // Extract meshes
       s = this.asset.mNumMeshes();
       for( int i = 0; i < s; i++ )
       this.meshes.get(i).setAsset(AIMesh.create(this.asset.mMeshes().get(i)));
       
       Assimp.aiReleaseImport(this.asset);
       
       this.deloadImpl();
       
       //this.asset.mSkeletons()
       //this.asset.mTextures()
    }

    @Override
    protected void deloadImpl() {
        this.animations = null;
        this.materials = null;
        this.meshes = null;
    }
    
    
    public SceneObjectAsset setImportFlags(int importFlags) {
        this.importFlags = importFlags;
        return this;
    }
    
    public SceneObjectAsset addAnimation(Animation animation) {
        this.animations.add(animation);
        return this;
    }
    
    public SceneObjectAsset addMaterial(Material material) {
        this.materials.add(material);
        return this;
    }
    
    public SceneObjectAsset addMesh(Mesh mesh) {
        this.meshes.add(mesh);
        return this;
    }
}
*/