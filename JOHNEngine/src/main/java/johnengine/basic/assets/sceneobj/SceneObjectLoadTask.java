package johnengine.basic.assets.sceneobj;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIAnimation;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AINode;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import johnengine.basic.BasicUtils;
import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.assets.animation.Animation;
import johnengine.basic.assets.animation.NodeTEMP;
import johnengine.basic.assets.animation.Skeleton;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.core.assetmngr.asset.ALoadTask;

public class SceneObjectLoadTask extends ALoadTask {
    
    /********************* ExpectedMesh-class *********************/
    
    private class ExpectedMesh {
        public final Mesh mesh;
        public final IGraphicsStrategy graphicsStrategy;
        
        private ExpectedMesh(Mesh mesh, IGraphicsStrategy graphicsStrategy) {
            this.mesh = mesh;
            this.graphicsStrategy = graphicsStrategy;
        }
    }
    
    
    /********************* SceneObjectLoadTask-class *********************/
    
    public static final int DEFAULT_IMPORT_FLAGS = 
        Assimp.aiProcess_JoinIdenticalVertices |
        Assimp.aiProcess_Triangulate |
        Assimp.aiProcess_FixInfacingNormals |
        Assimp.aiProcess_LimitBoneWeights |
        Assimp.aiProcess_CalcTangentSpace;/*|
        Assimp.aiProcess_PreTransformVertices;*/
    
    private final List<ExpectedMesh> expectedMeshes;
    private final List<Skeleton> expectedSkeletons;
    private final List<Animation> expectedAnimations;
    
    private int importFlags;

    public SceneObjectLoadTask(String path) {
        super(path);
        this.expectedMeshes = new ArrayList<>();
        this.expectedSkeletons = new ArrayList<>();
        this.expectedAnimations = new ArrayList<>();
        this.importFlags = DEFAULT_IMPORT_FLAGS;
    }
    
    public SceneObjectLoadTask() {
        this(null);
    }
    
    
    @Override
    protected void loadImpl() {
        AIScene aiScene = Assimp.aiImportFile(this.getPath(), this.importFlags);
        int s;
        
            // Extract meshes and skeletons
        s = Math.min(this.expectedMeshes.size(), aiScene.mNumMeshes());
        for( int i = 0; i < s; i++ )
        {
            ExpectedMesh expectedMesh = this.expectedMeshes.get(i);
            AIMesh aiMesh = AIMesh.create(aiScene.mMeshes().get(i));
            Mesh.createMesh(expectedMesh.mesh, aiMesh);
            expectedMesh.mesh.setGraphicsStrategy(expectedMesh.graphicsStrategy);
            expectedMesh.graphicsStrategy.loaded();
            
            if( i < this.expectedSkeletons.size() )
            Skeleton.createSkeleton(this.expectedSkeletons.get(i), aiMesh);
        }
        
            // Extract animations, if there are any
        s = Math.min(this.expectedAnimations.size(), aiScene.mNumAnimations());
        
        if( s > 0 )
        {
            AINode aiRootNode = aiScene.mRootNode();
            NodeTEMP rootNode = this.recurseAINodes(aiRootNode, null); 
            Matrix4f globalTransformation = 
                BasicUtils.aiMatrix4x4ToMatrix4f(aiRootNode.mTransformation()).invert();
            
            PointerBuffer aiAnimations = aiScene.mAnimations();
            for( int i = 0; i < s; i++ )
            {
                AIAnimation aiAnimation = AIAnimation.create(aiAnimations.get(i));
                Animation.createAnimation(
                    this.expectedAnimations.get(i), 
                    aiAnimation, 
                    globalTransformation, 
                    rootNode, 
                    this.expectedSkeletons.get(0).getBones()
                );
            }
        }
        
        Assimp.aiReleaseImport(aiScene);
    }
    
    private NodeTEMP recurseAINodes(AINode aiNode, NodeTEMP parentNode) {
        String nodeName = aiNode.mName().dataString();
        Matrix4f nodeTransform = 
            BasicUtils.aiMatrix4x4ToMatrix4f(aiNode.mTransformation());
        NodeTEMP node = new NodeTEMP(nodeName, parentNode, nodeTransform);
        
        PointerBuffer aiChildren = aiNode.mChildren();
        int s = aiNode.mNumChildren();
        for( int i = 0; i < s; i++ )
        {
            AINode aiChild = AINode.create(aiChildren.get(i));
            NodeTEMP child = this.recurseAINodes(aiChild, node);
            node.addChild(child);
        }
        
        return node;
    }
    
    public SceneObjectLoadTask expectMesh(Mesh mesh, IGraphicsStrategy graphicsStrategy) {
        this.expectedMeshes.add(new ExpectedMesh(mesh, graphicsStrategy));
        return this;
    }
    
    public SceneObjectLoadTask expectAnimation(Animation animation) {
        this.expectedAnimations.add(animation);
        return this;
    }
    
    public void setImportFlags(int importFlags) {
        this.importFlags = importFlags;
    }
}
