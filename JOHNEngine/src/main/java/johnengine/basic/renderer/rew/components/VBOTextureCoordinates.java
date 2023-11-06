package johnengine.basic.renderer.rew.components;

import java.nio.FloatBuffer;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class VBOTextureCoordinates extends AVertexBufferObject<Vector2f[]> {

    public static final int SIZE = 2;
    
    public VBOTextureCoordinates(Vector2f[] data) {
        super(SIZE);
        this.data = data;
    }

    
    @Override
    public void generate() {
        int target = GL30.GL_ARRAY_BUFFER;
        this.handle = GL30.glGenBuffers();
        this.bind();
        
        FloatBuffer uvBuffer = MemoryUtil.memAllocFloat(this.data.length * this.size);
        for( Vector2f uv : this.data )
        {
            uvBuffer.put(uv.x());
            uvBuffer.put(uv.y());
        }
        
        GL30.glBufferData(target, uvBuffer, GL30.GL_STATIC_DRAW);
        GL30.glEnableVertexAttribArray(this.attributeIndex);
        GL30.glVertexAttribPointer(this.attributeIndex, this.size, GL30.GL_FLOAT, false, 0, 0);
        
        MemoryUtil.memFree(uvBuffer);
    }

}
