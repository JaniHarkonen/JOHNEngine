package johnengine.basic.renderer.components;

import java.nio.FloatBuffer;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

public final class VBOTextureCoordinates extends AVBO<Vector2f[]> {

    public static final int SIZE = 2;
    
    public VBOTextureCoordinates(int attributeIndex) {  
        super(GL30.GL_ARRAY_BUFFER, SIZE, attributeIndex);
    }

    
    @Override
    public void generate(Vector2f[] data) {
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
        //GL30.glEnableVertexAttribArray(this.attributeIndex);
        //GL30.glVertexAttribPointer(this.attributeIndex, this.size, GL30.GL_FLOAT, false, 0, 0);
        
        this.freeAllocation(uvBuffer);
        this.unbind();
    }
}
