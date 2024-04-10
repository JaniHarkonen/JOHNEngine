package johnengine.basic.assets.animation;

import org.joml.Matrix4f;

public class Bone {

    public static class Weight {
        public final int boneID;
        public final int vertexID;
        public float value;
        
        public Weight(int boneID, int vertexID, float value) {
            this.boneID = boneID;
            this.vertexID = vertexID;
            this.value = value;
        }
    }
    
    
    public static final int MAX_WEIGHT_PER_VERTEX_COUNT = 4;
    
    private int id;
    private String name;
    private Matrix4f offsetMatrix;
    
    public Bone(int id, String name, Matrix4f offsetMatrix) {
        this.id = id;
        this.name = name;
        this.offsetMatrix = offsetMatrix;
    }
    
    
    public int getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Matrix4f getOffsetMatrix() {
        return this.offsetMatrix;
    }
}
