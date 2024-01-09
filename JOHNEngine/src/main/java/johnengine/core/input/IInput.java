package johnengine.core.input;

public interface IInput {

    public interface State {
        public void takeSnapshot(IInput.State dest);
    }
    
    public interface Event<T> {
        public boolean check(IInput.State targetState);
        
        public T getValue();
    }
    
    public void setup();
    
    public void snapshot();
    
    public IInput.State getState();
}
