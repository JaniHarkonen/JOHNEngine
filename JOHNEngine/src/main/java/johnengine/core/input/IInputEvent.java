package johnengine.core.input;

public interface IInputEvent<T> {

    public boolean check(IInput.State state);
    
    public T getValue();
}
