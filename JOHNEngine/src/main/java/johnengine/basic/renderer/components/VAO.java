package johnengine.basic.renderer.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

import johnengine.basic.assets.IBindable;
import johnengine.basic.assets.IGeneratable;
import johnengine.testing.DebugUtils;

public class VAO implements IGeneratable, IBindable {
    
    private int handle;
    private List<AVBO<?>> vbos;
    private VBOIndices indicesVBO;
    
    public VAO() {
        this.handle = 0;
        this.vbos = new ArrayList<AVBO<?>>();
        this.indicesVBO = null;
    }

    
    @Override
    public boolean generate() {
        this.handle = GL30.glGenVertexArrays();
        this.bind();
        
            // Bind all VBOs, except the one holding the indices
        for( int i = 0; i < this.vbos.size(); i++ )
        {
            AVBO<?> vbo = this.vbos.get(i);
            
            if( vbo.getHandle() <= 0 )
            DebugUtils.log(this, "ERROR: trying to generate a VAO with one or more null VBOs!");
            
            vbo.bind();
            
            GL30.glEnableVertexAttribArray(i);
            GL30.glVertexAttribPointer(i, vbo.getSize(), GL30.GL_FLOAT, false, 0, 0);
            
            vbo.unbind();
        }
        
        this.indicesVBO.bind();
        this.indicesVBO.unbind();
        this.vbos = null;
        
        this.unbind();
        return true;
    }
    
    @Override
    public boolean bind() {
        GL30.glBindVertexArray(this.handle);
        return true;
    }
    
    @Override
    public boolean unbind() {
        GL30.glBindVertexArray(0);
        return true;
    }
    
    @Override
    public boolean dispose() {
        GL30.glDeleteVertexArrays(this.handle);
        this.handle = 0;
        this.vbos = null;
        
        return true;
    }
    
    public void addVBO(AVBO<?> vbo) {
        this.vbos.add(vbo);
    }
    
    public void setIndicesVBO(VBOIndices indicesVBO) {
        this.indicesVBO = indicesVBO;
    }
    
    
    public int getHandle() {
        return this.handle;
    }
}
