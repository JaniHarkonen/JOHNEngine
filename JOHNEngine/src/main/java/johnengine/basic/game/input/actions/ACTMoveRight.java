package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.Action;
import johnengine.basic.game.input.IControllable;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class ACTMoveRight extends AMove {
    
    public ACTMoveRight() {
        super(Action.MOVE_RIGHT);
    }

    @Override
    public void perform(IControllable target) {
        //target.moveRight(this.intensity);
        target.control(this);
    }

    @Override
    public <T> ACTMoveRight createInstance(AInputEvent<T> event, IInputConverter<T, ?> converter) {
        ACTMoveRight action = new ACTMoveRight();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
