package johnengine.basic.game.rewrite.actions;

import johnengine.basic.game.rewrite.AControllerAction;
import johnengine.basic.game.rewrite.IControllable;

public class ACTMoveLeft extends AMove {

    @Override
    public void perform(IControllable target) {
        target.moveLeft(this.intensity);
    }

    @Override
    public AControllerAction createInstance() {
        return new ACTMoveLeft();
    }
    
}
