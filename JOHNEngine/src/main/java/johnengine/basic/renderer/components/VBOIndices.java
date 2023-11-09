package johnengine.basic.renderer.components;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.renderer.asset.Mesh;

public class VBOIndices extends AVertexBufferObject<Mesh.Face[]> {

    public static final int SIZE = 3;
    
    public VBOIndices(Mesh.Face[] data) {
        super(SIZE);
        this.data = data;
    }

    
    @Override
    public void generate() {
        this.handle = GL30.glGenBuffers();
        this.bind();
        
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(this.data.length * this.size);
        for( Mesh.Face index : this.data )
        {
            indexBuffer.put(index.get(0));
            indexBuffer.put(index.get(1));
            indexBuffer.put(index.get(2));
        }
        
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL30.GL_STATIC_DRAW);
        MemoryUtil.memFree(indexBuffer);
    }
    
    @Override
    public void bind() {
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, this.handle);
    }
}
