package johnengine.basic.opengl.renderer.vaocache;

import johnengine.basic.opengl.renderer.asset.MeshGL;
import johnengine.basic.opengl.renderer.vao.VAO;
import johnengine.basic.opengl.renderer.vao.VBOType;
import johnengine.core.cache.TimedCache;

public class VAOCache extends TimedCache<MeshGL, VAO> {

    public VAOCache(long expirationTime) {
        super(expirationTime);
    }

    
    public VAO fetchVAO(MeshGL meshGraphics) {
        VAO vao = this.get(meshGraphics);
        
            // VAO found from the cache and return it
        if( vao != null )
        return vao;
        
            // Generate a new VAO and cache it
        MeshGL.VBOContainer vbos = meshGraphics.getVBOs();
        vao = new VAO();
        vao
        .addVBO(vbos.getVBO(VBOType.VERTICES))
        .addVBO(vbos.getVBO(VBOType.NORMALS))
        .addVBO(vbos.getVBO(VBOType.TANGENTS))
        .addVBO(vbos.getVBO(VBOType.BITANGENTS))
        .addVBO(vbos.getVBO(VBOType.UVS));
        vao.setIndicesVBO(vbos.getIndicesVBO());
        vao.generate();
        
        this.cacheItem(meshGraphics, vao);
        return vao;
    }
}
