package johnengine.core;

public interface IRenderBufferStrategy {
    public void prepare();
    
    public void dispose();
    
    public <I extends IRenderable> void executeStrategoid(I instance);
    
    public void startBuffer();
    
    public void endBuffer();
}
