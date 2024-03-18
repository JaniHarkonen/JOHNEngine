package johnengine.core.input;

public interface IInputConverter<O> {

    public O convert();
    
    //public void setEvent(IInput.Event<?> event);
    public void setEvent(AInputEvent<?> event);
    
    //public default O convert(IInput.Event<?> event) {
    public default O convert(AInputEvent<?> event) {
        this.setEvent(event);
        return this.convert();
    }
}
