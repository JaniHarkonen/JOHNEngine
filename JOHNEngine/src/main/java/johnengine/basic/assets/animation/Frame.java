package johnengine.basic.assets.animation;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIAnimation;
import org.lwjgl.assimp.AINodeAnim;
import org.lwjgl.assimp.AIQuatKey;
import org.lwjgl.assimp.AIQuaternion;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.AIVectorKey;

public class Frame {
    
    public static final int MAX_BONE_COUNT = 150;
    
    public static void createFrame(
        AIAnimation aiAnimation, 
        Frame dest, 
        NodeTEMP node, 
        Matrix4f parentTransformation, 
        Matrix4f globalTransformation,
        List<Bone> sceneBones
    ) {
        String nodeName = node.name;
        AINodeAnim aiAnimationNode = findAnimationNode(aiAnimation, nodeName);
        Matrix4f nodeTransform = node.transform;
        
        if( aiAnimationNode != null )
        nodeTransform = createFrameTransformationMatrix(aiAnimationNode, dest);
        
        Matrix4f nodeGlobalTransformation = new Matrix4f(parentTransformation).mul(nodeTransform);
        for( Bone bone : sceneBones )
        {
                // Only accept bones affected by the frame transform
            if( !bone.getName().equals(nodeName) )
            continue;
            
            Matrix4f boneTransform = new Matrix4f(globalTransformation);
            boneTransform
            .mul(nodeGlobalTransformation)
            .mul(bone.getOffsetMatrix());
            
            dest.boneTransforms[bone.getID()] = boneTransform;
        }
        
        for( NodeTEMP child : node.children )
        {
            Frame.createFrame(
                aiAnimation, dest, child, nodeGlobalTransformation, globalTransformation, sceneBones
            );
        }
    }
    
    private static AINodeAnim findAnimationNode(AIAnimation aiAnimation, String nodeName) {
        PointerBuffer aiChannels = aiAnimation.mChannels();
        int s = aiAnimation.mNumChannels();
        for( int i = 0; i < s; i++ )
        {
            AINodeAnim aiAnimationNode = AINodeAnim.create(aiChannels.get(i));
            
            if( nodeName.equals(aiAnimationNode.mNodeName().dataString()) )
            return aiAnimationNode;
        }
        
        return null;
    }
    
    private static Matrix4f createFrameTransformationMatrix(AINodeAnim aiAnimationNode, Frame frame) {
        Matrix4f transformation = new Matrix4f();
        
        AIVectorKey.Buffer positionKeys = aiAnimationNode.mPositionKeys();
        AIQuatKey.Buffer rotationKeys = aiAnimationNode.mRotationKeys();
        AIVectorKey.Buffer scalingKeys = aiAnimationNode.mScalingKeys();
        
        AIVectorKey aiVectorKey;
        AIVector3D aiVector;
        int keyCount;
        
        keyCount = aiAnimationNode.mNumPositionKeys();
        if( keyCount > 0 )
        {
            aiVectorKey = positionKeys.get(Math.min(keyCount - 1, frame.frameIndex));
            aiVector = aiVectorKey.mValue();
            transformation.translate(aiVector.x(), aiVector.y(), aiVector.z());
        }
        
        keyCount = aiAnimationNode.mNumRotationKeys();
        if( keyCount > 0 )
        {
            AIQuatKey aiQuaternionKey = rotationKeys.get(Math.min(keyCount -1 , frame.frameIndex));
            AIQuaternion aiQuaternion = aiQuaternionKey.mValue();
            transformation.rotate(
                new Quaternionf(aiQuaternion.x(), aiQuaternion.y(), aiQuaternion.z(), aiQuaternion.w())
            );
        }
        
        keyCount = aiAnimationNode.mNumScalingKeys();
        if( keyCount > 0 )
        {
            aiVectorKey = scalingKeys.get(Math.min(keyCount - 1, frame.frameIndex));
            aiVector = aiVectorKey.mValue();
            transformation.scale(aiVector.x(), aiVector.y(), aiVector.z());
        }
        
        return transformation;
    }
    
    
    private Matrix4f[] boneTransforms;
    private int frameIndex;
    
    public Frame(int frameIndex) {
        this.boneTransforms = new Matrix4f[Frame.MAX_BONE_COUNT];
        this.frameIndex = frameIndex;
        
        for( int i = 0; i < this.boneTransforms.length; i++ )
        this.boneTransforms[i] = new Matrix4f();
    }
    
    
    public Frame() {
        this(-1);
    }
    
    
    private void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }
}
