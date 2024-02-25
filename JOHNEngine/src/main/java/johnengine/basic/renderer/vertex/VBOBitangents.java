package johnengine.basic.renderer.vertex;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

public final class VBOBitangents extends AVBO<Vector3f[]> {

    public static final int SIZE = 3;
    
    public VBOBitangents() {
        super(GL46.GL_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public boolean generate() {
        Vector3f[] data = this.source;
        
        this.genBuffers();
        this.bind();
        FloatBuffer bitangentBuffer = this.allocateFloatBuffer(data.length);
        
        for( Vector3f vertex : data )
        {
            bitangentBuffer.put(vertex.x());
            bitangentBuffer.put(vertex.y());
            bitangentBuffer.put(vertex.z());
        }
        
        bitangentBuffer.flip();
        
        GL46.glBufferData(this.target, bitangentBuffer, GL46.GL_STATIC_DRAW);
        
        this.freeAllocation(bitangentBuffer);
        this.unbind();
        
        return true;
    }
}
