package johnengine.basic.game.rewrite;

import johnengine.core.input.IInputEvent;

public interface IInputConverter<I, O> {

    public abstract O convert();
    
    public abstract void setEvent(IInputEvent<I> event);
}
