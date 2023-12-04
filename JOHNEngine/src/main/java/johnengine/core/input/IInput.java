package johnengine.core.input;

public interface IInput {

    public interface State<S> {
        public void takeSnapshot(S dest);
        public boolean isKeyDown(int key);
        public boolean isKeyReleased(int key);
        public boolean isMouseDown(int mouseButton);
        public boolean isMouseReleased(int mouseButton);
        public double getMouseX();
        public double getMouseY();
    }
    
    public void setup();
    public void snapshot();
    public IInput.State<?> getState();
}