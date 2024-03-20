package johnengine.basic.opengl.renderer.strgui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import johnengine.core.renderer.IRenderBuffer;

public class RenderBuffer implements IRenderBuffer<RenderBuffer> {
    
    private List<RenderElement> buffer;
    private Matrix4f projectionMatrix;
    
    public RenderBuffer() {
        this.buffer = new ArrayList<>();
        this.projectionMatrix = new Matrix4f();
    }

    
    @Override
    public RenderBuffer createInstance() {
        return new RenderBuffer();
    }
    
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    
    public void addRenderElement(RenderElement renderElement) {
        this.buffer.add(renderElement);
    }
    
    public List<RenderElement> getBuffer() {
        return this.buffer;
    }
    
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
}
