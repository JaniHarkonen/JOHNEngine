package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.Action;
import johnengine.basic.game.input.IControllable;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class ACTGUIEvent extends AControllerAction {

    
    
    public ACTGUIEvent() {
        super(Action.GUI_EVENT);
    }

    @Override
    public void perform(IControllable target) {
        target.control(this);
    }

    @Override
    public <T> ACTGUIEvent createInstance(
        AInputEvent<T> event, IInputConverter<T, ?> converter
    ) {
        ACTGUIEvent guiEvent = new ACTGUIEvent();
        return null;
    }
}
