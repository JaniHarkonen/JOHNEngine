package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.Action;
import johnengine.basic.game.input.IControllable;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class ACTMoveForward extends AMove {

    public ACTMoveForward() {
        super(Action.MOVE_FORWARD);
    }
    
    @Override
    public void perform(IControllable target) {
        target.control(this);
    }

    @Override
    public <T> ACTMoveForward createInstance(
        AInputEvent<T> event, IInputConverter<T, ?> converter
    ) {
        ACTMoveForward action = new ACTMoveForward();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
