package johnengine.basic.assets;

public interface IGraphicsAsset<T> extends IGeneratable {
    public IGraphicsAsset<T> createInstance(IRendererAsset asset);
    
    public T getData();
}
