package johnengine.basic.game.rewrite;

import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public abstract class AControllerAction {
    
    public abstract void perform(IControllable target);
    
    public abstract AControllerAction createInstance(IInput.Event<?> event, IInputConverter<?> converter);
}
