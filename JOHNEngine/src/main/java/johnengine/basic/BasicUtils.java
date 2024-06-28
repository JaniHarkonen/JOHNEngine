package johnengine.basic;

import org.joml.Matrix4f;
import org.lwjgl.assimp.AIMatrix4x4;

/**
 * This class contains general utility methods that are to be 
 * used by the 'basic'-package as well as its subpackages.
 */
public final class BasicUtils {

        // Do NOT instantiate
    private BasicUtils() { }
    
    /**
     * Converts LWJGL's Assimp {@link org.lwjgl.assimp.AIMatrix4x4 AIMatrix4x4}
     * 4x4 matrix into JOML's {@link org.joml.Matrix4f Matrix4f}. A new Matrix4f
     * is created whose each element is set to a corresponding AIMatrix4x4 one.
     * <br/><br/>
     * This is useful when Assimp's matrices are needed elsewhere in the program,
     * such as when bone weight matrices are loaded in. 
     * 
     * @param aiMatrix4x4 AIMatrix4x4 instance that is to be converted into a 
     * Matrix4f.
     * 
     * @return A new Matrix4f instance that is an exact copy of the AIMatrix4x4.
     * 
     */
    public static Matrix4f aiMatrix4x4ToMatrix4f(AIMatrix4x4 aiMatrix4x4) {
        Matrix4f matrix4f = new Matrix4f();
        
        matrix4f.m00(aiMatrix4x4.a1());
        matrix4f.m10(aiMatrix4x4.a2());
        matrix4f.m20(aiMatrix4x4.a3());
        matrix4f.m30(aiMatrix4x4.a4());
        matrix4f.m01(aiMatrix4x4.b1());
        matrix4f.m11(aiMatrix4x4.b2());
        matrix4f.m21(aiMatrix4x4.b3());
        matrix4f.m31(aiMatrix4x4.b4());
        matrix4f.m02(aiMatrix4x4.c1());
        matrix4f.m12(aiMatrix4x4.c2());
        matrix4f.m22(aiMatrix4x4.c3());
        matrix4f.m32(aiMatrix4x4.c4());
        matrix4f.m03(aiMatrix4x4.d1());
        matrix4f.m13(aiMatrix4x4.d2());
        matrix4f.m23(aiMatrix4x4.d3());
        matrix4f.m33(aiMatrix4x4.d4());
        
        return matrix4f;
    }
}
