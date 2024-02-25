package johnengine.basic.renderer.vertex;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL46;

import johnengine.basic.renderer.asset.Mesh;

public final class VBOIndices extends AVBO<Mesh.Face[]> {

    public static final int SIZE = 3;
    
    public VBOIndices() {
        super(GL46.GL_ELEMENT_ARRAY_BUFFER, SIZE);
    }

    
    @Override
    public boolean generate() {
        Mesh.Face[] data = this.source;
        
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
        
        GL46.glBufferData(this.target, indexBuffer, GL46.GL_STATIC_DRAW);
        
        this.freeAllocation(indexBuffer);
        this.unbind();
        
        return true;
    }
}
