package johnengine.basic.renderer.vao;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;
import johnengine.testing.DebugUtils;

public class VAO implements IGeneratable, IBindable {
    
    private int handle;
    private List<AVBO<?, ?>> vbos;
    private VBOIndices indicesVBO;
    
    public VAO() {
        this.handle = 0;
        this.vbos = new ArrayList<>();
        this.indicesVBO = null;
    }

    
    @Override
    public boolean generate() {
        this.handle = GL46.glGenVertexArrays();
        this.bind();
        
            // Bind all VBOs, except the one holding the indices
        for( int i = 0; i < this.vbos.size(); i++ )
        {
            AVBO<?, ?> vbo = this.vbos.get(i);
            
            if( vbo.getHandle() <= 0 )
            DebugUtils.log(this, "ERROR: trying to generate a VAO with one or more null VBOs!");
            
            vbo.bind();
            
            GL46.glEnableVertexAttribArray(i);
            GL46.glVertexAttribPointer(i, vbo.getSize(), GL46.GL_FLOAT, false, 0, 0);
            
            vbo.unbind();
        }
        
        this.indicesVBO.bind();
        this.indicesVBO.unbind();
        this.vbos = null; // Set to null to free-up space as the VBOs are no longer needed
        
        this.unbind();
        return true;
    }
    
    @Override
    public boolean bind() {
        GL46.glBindVertexArray(this.handle);
        return true;
    }
    
    @Override
    public boolean unbind() {
        GL46.glBindVertexArray(0);
        return true;
    }
    
    @Override
    public boolean dispose() {
        GL46.glDeleteVertexArrays(this.handle);
        this.handle = 0;
        this.vbos = null;
        
        return true;
    }
    
    public VAO addVBO(AVBO<?, ?> vbo) {
        this.vbos.add(vbo);
        return this;
    }
    
    public void setIndicesVBO(VBOIndices indicesVBO) {
        this.indicesVBO = indicesVBO;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
