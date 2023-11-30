package johnengine.basic.renderer.components.rewrite;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;

import johnengine.basic.renderer.asset.rewrite.Mesh;
import johnengine.basic.renderer.components.AVBO;

public final class VBOIndices extends AVBO<Mesh.Face[]> {

    public static final int SIZE = 3;
    
    public VBOIndices() {
        super(GL30.GL_ELEMENT_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public void generate(Mesh.Face[] data) {
        this.genBuffers();
        this.bind();
        IntBuffer indexBuffer = this.allocateIntBuffer(data.length);
        
        for( Mesh.Face face : data )
        {
            indexBuffer.put(face.getIndex(0));
            indexBuffer.put(face.getIndex(1));
            indexBuffer.put(face.getIndex(2));
        }
        
        indexBuffer.flip();
        
        GL30.glBufferData(this.target, indexBuffer, GL30.GL_STATIC_DRAW);
        
        this.freeAllocation(indexBuffer);
        this.unbind();
    }
}
