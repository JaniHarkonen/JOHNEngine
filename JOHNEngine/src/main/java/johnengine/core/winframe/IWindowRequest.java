package johnengine.core.winframe;

public interface IWindowRequest {

    public void fulfill(IWindow.Properties affectedProperties);
    
    public String getPropertyKey();
}
