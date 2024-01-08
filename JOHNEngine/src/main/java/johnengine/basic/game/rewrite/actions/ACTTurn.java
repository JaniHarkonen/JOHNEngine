package johnengine.basic.game.rewrite.actions;

import johnengine.basic.game.rewrite.AControllerAction;
import johnengine.basic.game.rewrite.IControllable;

public class ACTTurn extends AControllerAction {
    
    public float deltaX;
    public float deltaY;

    @Override
    public void perform(IControllable target) {
        target.turn(this.deltaX, this.deltaY);
    }

    @Override
    public AControllerAction createInstance() {
        return new ACTTurn();
    }
}
