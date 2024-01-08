package johnengine.basic.game.rewrite;

import johnengine.core.input.IInputEvent;

public abstract class AInputConverter<I, O> {
    
    public abstract O convert(IInputEvent<?> inputEvent);
    
    public abstract O convert();
    
    public abstract void setEvent(IInputEvent<I> event);
}
