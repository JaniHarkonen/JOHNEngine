package johnengine.basic.game.rewrite.actions;

import johnengine.basic.game.rewrite.IControllable;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ACTMoveForward extends AMove {

    @Override
    public void perform(IControllable target) {
        target.moveForward(this.intensity);
    }

    @Override
    public ACTMoveForward createInstance(IInput.Event<?> event, IInputConverter<?> converter) {
        ACTMoveForward action = new ACTMoveForward();
        action.setIntensityFromInputEvent(event, converter);
        return action;
    }
}
