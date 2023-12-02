package johnengine.basic.renderer.components;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public final class VBOVertices extends AVBO<Vector3f[]> {

    public static final int SIZE = 3;
    
    public VBOVertices() {
        super(GL30.GL_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public boolean generate() {
        Vector3f[] data = this.source;
        
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
        
        this.freeAllocation(vertexBuffer);
        this.unbind();
        
        return true;
    }
}
