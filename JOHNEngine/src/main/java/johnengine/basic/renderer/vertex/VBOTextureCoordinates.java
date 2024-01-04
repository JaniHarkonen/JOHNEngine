package johnengine.basic.renderer.vertex;

import java.nio.FloatBuffer;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

public final class VBOTextureCoordinates extends AVBO<Vector2f[]> {

    public static final int SIZE = 2;
    
    public VBOTextureCoordinates() {  
        super(GL30.GL_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public boolean generate() {
        Vector2f[] data = this.source;
        
        this.genBuffers();
        this.bind();
        FloatBuffer uvBuffer = this.allocateFloatBuffer(data.length);
        
        for( Vector2f uv : data )
        {
            uvBuffer.put(uv.x());
            uvBuffer.put(uv.y());
        }
        
        uvBuffer.flip();
        
        GL30.glBufferData(this.target, uvBuffer, GL30.GL_STATIC_DRAW);
        
        this.freeAllocation(uvBuffer);
        this.unbind();
        
        return true;
    }
}