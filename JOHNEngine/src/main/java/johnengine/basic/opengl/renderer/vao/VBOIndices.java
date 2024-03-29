package johnengine.basic.opengl.renderer.vao;

import org.lwjgl.opengl.GL46;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.opengl.renderer.buffers.IntBufferGL;

public class VBOIndices extends AVBO<Mesh.Face, Integer> {

    public VBOIndices() {
        super(GL46.GL_ELEMENT_ARRAY_BUFFER, 3, new IntBufferGL());
    }

    
    @Override
    protected void populateBuffer() {
        for( Mesh.Face face : this.data )
        {
            this.buffer.put(face.getIndex(0));
            this.buffer.put(face.getIndex(1));
            this.buffer.put(face.getIndex(2));
        }
    }
}
