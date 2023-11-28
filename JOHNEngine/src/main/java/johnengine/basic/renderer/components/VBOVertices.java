package johnengine.basic.renderer.components;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public final class VBOVertices extends AVBO<Vector3f[]> {

    public static final int SIZE = 3;
    
    public VBOVertices(int attributeIndex) {
        super(GL30.GL_ARRAY_BUFFER, SIZE, attributeIndex);
    }

    
    @Override
    public void generate(Vector3f[] data) {
        this.genBuffers();
        this.bind();
        FloatBuffer vertexBuffer = this.allocateFloatBuffer(data.length);
        
        for( Vector3f vertex : data )
        {
            vertexBuffer.put(vertex.x());
            vertexBuffer.put(vertex.y());
            vertexBuffer.put(vertex.z());
        }
        
        vertexBuffer.flip();
        
        GL30.glBufferData(this.target, vertexBuffer, GL30.GL_STATIC_DRAW);
        //GL30.glEnableVertexAttribArray(this.attributeIndex);
        //GL30.glVertexAttribPointer(this.attributeIndex, this.size, GL30.GL_FLOAT, false, 0, 0);
        
        this.freeAllocation(vertexBuffer);
        this.unbind();
    }
}
