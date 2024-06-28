package johnengine.basic.assets.animation;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIAnimation;
import org.lwjgl.assimp.AINodeAnim;

public class Animation {
    
    public static void createAnimation(
        Animation dest, 
        AIAnimation aiAnimation, 
        Matrix4f globalTransformation, 
        NodeTEMP sceneNode, 
        List<Bone> sceneBones
    ) {
            // Find the number of frames in an animation by
            // iterating over the nodes of all animation 
            // channels and finding the highest number of 
            // (position, rotation or scaling) keys
        PointerBuffer aiChannels = aiAnimation.mChannels();
        int frameCount = 0;
        int s = aiAnimation.mNumChannels();
        for( int i = 0; i < s; i++ )
        {
            AINodeAnim aiNodeAnim = AINodeAnim.create(aiChannels.get(i));
            int highestKeyCount = Math.max(
                aiNodeAnim.mNumPositionKeys(), 
                Math.max(aiNodeAnim.mNumRotationKeys(), aiNodeAnim.mNumScalingKeys())
            );
            
            frameCount = Math.max(frameCount, highestKeyCount);
        }
        
        List<Frame> frames = new ArrayList<>();
        for( int i = 0; i < frameCount; i++ )
        {
            Frame frame = new Frame(i);
            Frame.createFrame(
                aiAnimation, 
                frame, 
                sceneNode, 
                sceneNode.transform, 
                globalTransformation, 
                sceneBones
            );
            frames.add(frame);
        }
        
        dest.duration = aiAnimation.mDuration();
        dest.frames = frames;
    }
    
    
    private String name;
    private List<Frame> frames;
    private int frameCount;
    private double duration;
    
    public Animation(String name) {
        this.name = name;
        this.frames = new ArrayList<>();
        this.frameCount = 0;
        this.duration = 0.0d;
    }
    
    
    private void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }
}
