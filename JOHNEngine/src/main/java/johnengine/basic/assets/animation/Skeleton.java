package johnengine.basic.assets.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIBone;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVertexWeight;

import johnengine.basic.BasicUtils;

public class Skeleton {

    public static void createSkeleton(Skeleton dest, AIMesh aiMesh) {
        if( dest == null || aiMesh == null )
        return;
        
        List<Bone> bones = new ArrayList<>();
        List<Integer> boneIDs = new ArrayList<>();
        List<Float> weights = new ArrayList<>();
        
        Map<Integer, List<Bone.Weight>> weightSet = new HashMap<>();
        int s = aiMesh.mNumBones();
        PointerBuffer aiBones = aiMesh.mBones();
        for( int i = 0; i < s; i++ )
        {
            AIBone aiBone = AIBone.create(aiBones.get(i));
            int boneID = bones.size();
            Bone bone = new Bone(
                boneID, 
                aiBone.mName().dataString(), 
                BasicUtils.aiMatrix4x4ToMatrix4f(aiBone.mOffsetMatrix())
            );
            bones.add(bone);
            
            int weightCount = aiBone.mNumWeights();
            AIVertexWeight.Buffer aiWeights = aiBone.mWeights();
            for( int j = 0; j < weightCount; j++ )
            {
                AIVertexWeight aiWeight = aiWeights.get(j);
                Bone.Weight boneWeight = new Bone.Weight(
                    boneID, aiWeight.mVertexId(), aiWeight.mWeight()
                );
                List<Bone.Weight> weightList = weightSet.get(boneWeight.vertexID);
                
                if( weightList == null )
                {
                    weightList = new ArrayList<>();
                    weightSet.put(boneWeight.vertexID, weightList);
                }
                
                weightList.add(boneWeight);
            }
        }
        
        s = aiMesh.mNumVertices();
        for(int i = 0; i < s; i++)
        {
            List<Bone.Weight> weightList = weightSet.get(i);
            int size = (weightList != null) ? weightList.size() : 0;
            for( int j = 0; j < Bone.MAX_WEIGHT_PER_VERTEX_COUNT; j++ )
            {
                float weightValue = 0.0f;
                int weightBoneID = 0;
                
                if( j < size )
                {
                    Bone.Weight weight = weightList.get(j);
                    weightValue = weight.value;
                    weightBoneID = weight.boneID;
                }
                
                weights.add(weightValue);
                boneIDs.add(weightBoneID);
            }
        }
        
        dest.bones = bones;
    }
    
    
    private List<Bone> bones;
    
    public Skeleton() {
        this.bones = new ArrayList<>();
    }
    
    
    public List<Bone> getBones() {
        return this.bones;
    }
}
