package johnengine.basic.renderer.vertex;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public final class VBONormals extends AVBO<Vector3f[]> {

    public static final int SIZE = 3;
    
    public VBONormals() {
        super(GL30.GL_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public boolean generate() {
        Vector3f[] data = this.source;
        
        this.genBuffers();
        this.bind();
        FloatBuffer normalBuffer = this.allocateFloatBuffer(data.length);
        
        for( Vector3f vertex : data )
        {
            normalBuffer.put(vertex.x());
            normalBuffer.put(vertex.y());
            normalBuffer.put(vertex.z());
        }
        
        normalBuffer.flip();
        
        GL30.glBufferData(this.target, normalBuffer, GL30.GL_STATIC_DRAW);
        
        this.freeAllocation(normalBuffer);
        this.unbind();
        
        return true;
    }
}
