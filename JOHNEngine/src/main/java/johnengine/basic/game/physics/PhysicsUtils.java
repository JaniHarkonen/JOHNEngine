package johnengine.basic.game.physics;

import org.joml.Math;
import org.joml.Vector3f;

public final class PhysicsUtils {
    public static final float INTERSECTION_EPSILON = 0.00001f;
    
    public static float calculateNormalizationScalar(float x, float y, float z) {
        return Math.invsqrt(Math.fma(x, x, Math.fma(y, y, z * z)));
    }
    
    public static float calculateNormalizationScalar(Vector3f v) {
        return calculateNormalizationScalar(v.x, v.y, v.z);
    }
    
    
    /**
     * Normalizes a given Vector3f using the Vector3f.normalize()-method
     * as long as at least one of the components is greater than 0.0f, 
     * making the vector have a non-zero length. This ensures that the 
     * resulting vector can never contain NaN values, hence the word 
     * 'safe'.
     * <br/><br/>
     * If the vector is of length 0.0f, it will not be normalized. 
     * 
     * @param v Vector3f to be normalized.
     * @return <b>v</b>, the Vector3f that was passed in. The vector
     * will be normalized if its length was greater than 0.0f.
     */
    public static Vector3f normalizeVector3fSafe(Vector3f v) {
        if( v.x > 0.0f || v.y > 0.0f || v.z > 0.0f )
        return v.normalize();
        
        return v;
    }
    
    /**
     * Shifts a float from a given value towards a goal value by a given
     * amount.
     * 
     * @param from Float value to shift from.
     * @param to Float value to shift towards.
     * @param amount Float amount by which to shift.
     * @return A float shifted from <b>from</b> towards <b>to</b> by the 
     * given amount. If the goal value was exceeded, <b>to</b> will be 
     * returned.
     */
    public static float shiftf(float from, float to, float amount) {
        if( to > from )
        return Math.min(to, from + Math.abs(amount));
        
        return Math.max(to, from - Math.abs(amount));
    }
    
    
    // Do NOT instantiate, utility class
    private PhysicsUtils() { }
}
