package johnengine.basic.game.rewrite.actions;

import johnengine.basic.game.rewrite.AInputConverter;
import johnengine.basic.game.rewrite.IControllable;
import johnengine.basic.game.rewrite.IInputConverter;
import johnengine.core.input.IInputEvent;

public class ACTMoveBackward extends AMove {


    @Override
    public void perform(IControllable target, IInputEvent<?> inputEvent, IInputConverter<?, ?> converter) {
        IInputConverter<?, ?> c = (IInputConverter<?, ?>) converter; 
        target.moveBackward(c.convert(inputEvent));
    }
}
