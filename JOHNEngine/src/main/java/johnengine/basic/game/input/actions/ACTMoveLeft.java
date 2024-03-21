package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.Action;
import johnengine.basic.game.input.IControllable;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class ACTMoveLeft extends AMove {
    
    public ACTMoveLeft() {
        super(Action.MOVE_LEFT);
    }
    
    @Override
    public void perform(IControllable target) {
        //target.moveLeft(this.intensity);
        target.control(this);
    }

    @Override
    public <T> ACTMoveLeft createInstance(AInputEvent<T> event, IInputConverter<T, ?> converter) {
        ACTMoveLeft action = new ACTMoveLeft();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
