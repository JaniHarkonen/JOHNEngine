package johnengine.basic.renderer.vaocache;

import johnengine.basic.renderer.asset.MeshGL;
import johnengine.basic.renderer.asset.MeshGL.VBOContainer;
import johnengine.basic.renderer.vertex.VAO;
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
        VBOContainer vbos = meshGraphics.getData();
        vao = (new VAO())
        .addVBO(vbos.getVerticesVBO())
        .addVBO(vbos.getNormalsVBO())
        .addVBO(vbos.getTangentsVBO())
        .addVBO(vbos.getBitangentsVBO())
        .addVBO(vbos.getTextureCoordinatesVBO());
        vao.setIndicesVBO(vbos.getIndicesVBO());
        vao.generate();
        
        this.cacheItem(meshGraphics, vao);
        return vao;
    }
}
