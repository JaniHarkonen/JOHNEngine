package johnengine.basic.opengl.renderer.strgui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import johnengine.core.renderer.IRenderBuffer;

public class RenderBuffer implements
    IRenderBuffer<RenderBuffer>,
    IHasRenderBuffer
{
    
    private List<RenderUnit> buffer;
    private Matrix4f projectionMatrix;
    
    public RenderBuffer() {
        this.buffer = new ArrayList<>();
        this.projectionMatrix = new Matrix4f();
    }

    
    @Override
    public RenderBuffer createInstance() {
        return new RenderBuffer();
    }
    
    @Override
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    
    public void addRenderUnit(RenderUnit renderUnit) {
        this.buffer.add(renderUnit);
    }
    
    public List<RenderUnit> getBuffer() {
        return this.buffer;
    }
    
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
}
