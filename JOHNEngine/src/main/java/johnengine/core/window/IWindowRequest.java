package johnengine.core.window;

public interface IWindowRequest {

    public void fulfill(IWindow.Properties affectedProperties);
    
    public String getPropertyKey();
}
