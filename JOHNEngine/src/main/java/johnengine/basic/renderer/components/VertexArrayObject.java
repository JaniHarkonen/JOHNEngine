package johnengine.basic.renderer.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

public class VertexArrayObject extends AVertexCollection {
    private final List<AVertexBufferObject<?>> vbos;
    
    public VertexArrayObject() {
        super();
        this.vbos = new ArrayList<>();
    }

    
    @Override
    public void generate() {
        this.handle = GL30.glGenVertexArrays();
        this.bind();
        
        for( AVertexBufferObject<?> vbo : this.vbos )
        vbo.generate();
        
        this.unbind();
    }
    
    @Override
    public void bind() {
        GL30.glBindVertexArray(this.handle);
    }
    
    @Override
    public void unbind() {
        GL30.glBindVertexArray(0);
    }
    
    @Override
    public void free() {
        for( AVertexBufferObject<?> vbo : this.vbos )
        vbo.free();
        
        GL30.glDeleteVertexArrays(this.handle);
    }
    
    
    public void addVBO(AVertexBufferObject<?> vbo) {
        vbo.setIndex(this.vbos.size());
        this.vbos.add(vbo);
    }
}
