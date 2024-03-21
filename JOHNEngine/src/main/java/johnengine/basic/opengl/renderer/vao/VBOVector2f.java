package johnengine.basic.opengl.renderer.vao;

import org.joml.Vector2f;

import johnengine.basic.opengl.renderer.buffers.FloatBufferGL;

public class VBOVector2f extends AVBO<Vector2f, Float> {

    public VBOVector2f(int target) {
        super(target, 2, new FloatBufferGL());
    }

    
    @Override
    protected void populateBuffer() {
        for( Vector2f uv : this.data )
        {
            this.buffer.put(uv.x);
            this.buffer.put(uv.y);
        }
    }
}
