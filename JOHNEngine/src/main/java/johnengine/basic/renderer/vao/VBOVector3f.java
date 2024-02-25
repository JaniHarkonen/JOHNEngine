package johnengine.basic.renderer.vao;

import org.joml.Vector3f;

import johnengine.basic.renderer.buffers.FloatBufferGL;

public class VBOVector3f extends AVBO<Vector3f, Float> {

    public VBOVector3f(int target) {
        super(target, 3, new FloatBufferGL());
    }

    
    @Override
    protected void populateBuffer() {
        for( Vector3f vector : this.data )
        {
            this.buffer.put(vector.x);
            this.buffer.put(vector.y);
            this.buffer.put(vector.z);
        }
    }
}
