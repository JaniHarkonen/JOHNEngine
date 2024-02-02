package johnengine.basic.game.input;

import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public abstract class AControllerAction {
    
    public final Action action;
    
    protected AControllerAction(Action action) {
        this.action = action;
    }
    
    public abstract void perform(IControllable target);
    
    public abstract AControllerAction createInstance(
        IInput.Event<?> event, 
        IInputConverter<?> converter
    );
}
