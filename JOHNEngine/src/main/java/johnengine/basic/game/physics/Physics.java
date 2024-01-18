package johnengine.basic.game.physics;

import org.joml.Math;
import org.joml.Vector3f;

public class Physics {

    public static final float INTERSECTION_EPSILON = 0.00001f;
    
    public static float calculateNormalizationScalar(float x, float y, float z) {
        return Math.invsqrt(Math.fma(x, x, Math.fma(y, y, z * z)));
    }
    
    public static float calculateNormalizationScalar(Vector3f v) {
        
        return calculateNormalizationScalar(v.x, v.y, v.z);
    }
    
    
    
}
