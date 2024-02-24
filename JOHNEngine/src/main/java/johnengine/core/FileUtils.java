package johnengine.core;

public final class FileUtils {

    public static String normalizePathSlashes(String path) {
        String normalizedPath = path.replace("\\", "/");
        
        if( normalizedPath.charAt(0) == '/' )
        normalizedPath = normalizedPath.substring(1);
        
        if( normalizedPath.charAt(normalizedPath.length() - 1) == '/' )
        normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
            
        return normalizedPath;
    }
    
    public static String resolveAbsolutePath(String rootPath, String relativePath) {
        return normalizePathSlashes(rootPath) + "/" + normalizePathSlashes(relativePath);
    }
}
