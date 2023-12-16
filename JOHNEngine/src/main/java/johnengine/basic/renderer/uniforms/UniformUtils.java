package johnengine.basic.renderer.uniforms;

import java.util.function.Supplier;

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
    
    public static <U extends IUniform<?>> void fillArray(UniformArray<?, U> uniformArray, Supplier<U> generator) {
        for( int i = 0; i < uniformArray.getArray().length; i++ )
        uniformArray.setArrayIndex(i, generator.get()); 
    }
}
