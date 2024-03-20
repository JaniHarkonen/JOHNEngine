package johnengine.core.input;

public interface IInputConverter<I, O> {

    public O convert();
    
    public void setEvent(AInputEvent<I> event);
    
    public default O convert(AInputEvent<I> event) {
        this.setEvent(event);
        return this.convert();
    }
}
