package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.Action;
import johnengine.basic.game.input.IControllable;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class ACTMoveBackward extends AMove {
    
    public ACTMoveBackward() {
        super(Action.MOVE_BACKWARD);
    }
    
    @Override
    public void perform(IControllable target) {
        //target.moveBackward(this.intensity);
        target.control(this);
    }

    @Override
    public <T> ACTMoveBackward createInstance(AInputEvent<T> event, IInputConverter<T, ?> converter) {
        ACTMoveBackward action = new ACTMoveBackward();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
