package johnengine.basic.renderer.rewrite.strinst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import johnengine.basic.renderer.rewrite.asset.Mesh;
import johnengine.basic.renderer.rewrite.asset.Texture;

public class InstancedRenderBuffer {
    
    public static class TextureBuffer {
        private final Map<Texture, List<Vector3f>> textureMap;
        
        public TextureBuffer() {
            this.textureMap = new HashMap<Texture, List<Vector3f>>();
        }
        
        
        public List<Vector3f> addTexture(Texture texture) {
            List<Vector3f> positionList = new ArrayList<Vector3f>();
            this.textureMap.put(texture, positionList);
            return positionList;
        }
    }

    private Map<Mesh, TextureBuffer> meshMap;
    
    public InstancedRenderBuffer() {
        this.meshMap = new HashMap<Mesh, TextureBuffer>();
    }
    
    
    public TextureBuffer getTextureBuffer(Mesh mesh) {
        return this.meshMap.get(mesh);
    }
}
