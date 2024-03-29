package johnengine.basic.opengl.renderer.uniforms;

public final class UniformUtils {
    
    public static String addFieldName(String name, String fieldName) {
        return name + "_" + fieldName;
    }
    
    public static String addFieldNameToId(String identifier, String fieldName) {
        return identifier + "." + fieldName;
    }
    
    public static String addArrayIndex(String name, int arrayIndex) {
        return name + arrayIndex;
    }
    
    public static String addArrayIndexToId(String identifier, int arrayIndex) {
        return identifier + "[" + arrayIndex + "]";
    }
}
