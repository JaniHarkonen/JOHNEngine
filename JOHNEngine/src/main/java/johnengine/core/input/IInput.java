package johnengine.core.input;

import java.util.List;

import johnengine.core.window.IWindow;

public interface IInput {
    
    public void setup();
    
    public void dispose();
    
    public List<AInputEvent<?>> getEvents();
    
    public IWindow getWindow();
    
    public void update();
    
    public void pollEvents();
}
