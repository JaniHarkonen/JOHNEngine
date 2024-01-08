package johnengine.basic.game.rewrite;

import johnengine.core.input.IInputEvent;

public abstract class AControllerAction {
    
    public abstract void perform(IControllable target, IInputEvent<?> inputEvent, IInputConverter<?, ?> converter);
    
    //public abstract AControllerAction createInstance(IInputEvent<?> event, AInputConverter<?, ?> converter);
}
