package johnengine.basic.game.input.actions;

import johnengine.basic.game.IControllable;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ACTMoveLeft extends AMove {
    
    @Override
    public void perform(IControllable target) {
        target.moveLeft(this.intensity);
    }

    @Override
    public ACTMoveLeft createInstance(IInput.Event<?> event, IInputConverter<?> converter) {
        ACTMoveLeft action = new ACTMoveLeft();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
