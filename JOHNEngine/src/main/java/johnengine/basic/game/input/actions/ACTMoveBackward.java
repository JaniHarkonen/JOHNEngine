package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.IControllable;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ACTMoveBackward extends AMove {
    
    @Override
    public void perform(IControllable target) {
        target.moveBackward(this.intensity);
    }

    @Override
    public ACTMoveBackward createInstance(IInput.Event<?> event, IInputConverter<?> converter) {
        ACTMoveBackward action = new ACTMoveBackward();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
