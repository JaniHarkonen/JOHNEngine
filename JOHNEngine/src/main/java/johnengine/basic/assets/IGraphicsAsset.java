package johnengine.basic.assets;

public interface IGraphicsAsset<T> {
    public void generate();
    public void dispose();
    public IGraphicsAsset<T> createInstance(IRenderAsset asset);
    
    public T getData();
}
