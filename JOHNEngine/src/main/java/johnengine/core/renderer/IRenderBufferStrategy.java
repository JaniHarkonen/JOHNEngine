package johnengine.core.renderer;

public interface IRenderBufferStrategy {

    //public void execute(JWorld world, IRenderStrategy renderStrategy);
    public void execute(IRenderContext renderContext, IRenderStrategy renderStrategy);  
}
