package johnengine.basic.renderer.vertex;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

public final class VBOTangents extends AVBO<Vector3f[]> {

    public static final int SIZE = 3;
    
    public VBOTangents() {
        super(GL46.GL_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public boolean generate() {
        Vector3f[] data = this.source;
        
        this.genBuffers();
        this.bind();
        FloatBuffer tangentBuffer = this.allocateFloatBuffer(data.length);
        
        for( Vector3f vertex : data )
        {
            tangentBuffer.put(vertex.x());
            tangentBuffer.put(vertex.y());
            tangentBuffer.put(vertex.z());
        }
        
        tangentBuffer.flip();
        
        GL46.glBufferData(this.target, tangentBuffer, GL46.GL_STATIC_DRAW);
        
        this.freeAllocation(tangentBuffer);
        this.unbind();
        
        return true;
    }
}
