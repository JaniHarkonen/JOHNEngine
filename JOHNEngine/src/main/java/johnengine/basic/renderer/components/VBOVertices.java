package johnengine.basic.renderer.components;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class VBOVertices extends AVertexBufferObject<Vector3f[]> {

    public static final int SIZE = 3;
    
    public VBOVertices(Vector3f[] data) {
        super(SIZE);
        this.data = data;
    }

    
    @Override
    public void generate() {
        this.handle = GL30.glGenBuffers();
        this.bind();
        
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(this.data.length * this.size);
        for( Vector3f vertex : this.data )
        {
            vertexBuffer.put(vertex.x());
            vertexBuffer.put(vertex.y());
            vertexBuffer.put(vertex.z());
        }
        
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW);
        GL30.glEnableVertexAttribArray(this.attributeIndex);
        GL30.glVertexAttribPointer(this.attributeIndex, this.size, GL30.GL_FLOAT, false, 0, 0);
        
        MemoryUtil.memFree(vertexBuffer);
    }
}
