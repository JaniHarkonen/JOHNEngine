package johnengine.core.input;

import johnengine.core.winframe.IWindow;

public interface IInput {

    public interface State {
        public void takeSnapshot(IInput.State dest);
        public long getTimestamp();
        public IInput getInput();
    }
    
    public interface Event<T> {
        public boolean check(IInput.State targetState);
        
        public T getValue();
    }
    
    public void setup();
    
    public void snapshot();
    
    public IInput.State getState();
    
    public IWindow getWindow();
}
