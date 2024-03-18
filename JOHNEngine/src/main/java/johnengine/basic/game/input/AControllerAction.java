package johnengine.basic.game.input;

import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public abstract class AControllerAction {
    
    public final Action action;
    
    protected AControllerAction(Action action) {
        this.action = action;
    }
    
    public abstract void perform(IControllable target);
    
    public abstract AControllerAction createInstance(
        AInputEvent<?> event, 
        IInputConverter<?> converter
    );
}
