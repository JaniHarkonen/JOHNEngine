package johnengine.basic.assets;

public interface IGraphicsStrategy {
    public void loaded();
    
    public void deload();
    
    public IGraphicsStrategy duplicateStrategy();
}
