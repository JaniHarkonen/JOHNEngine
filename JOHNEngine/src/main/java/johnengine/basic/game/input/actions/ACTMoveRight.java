package johnengine.basic.game.input.actions;

import johnengine.basic.game.IControllable;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ACTMoveRight extends AMove {

    @Override
    public void perform(IControllable target) {
        target.moveRight(this.intensity);
    }

    @Override
    public ACTMoveRight createInstance(IInput.Event<?> event, IInputConverter<?> converter) {
        ACTMoveRight action = new ACTMoveRight();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
