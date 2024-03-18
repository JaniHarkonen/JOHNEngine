package johnengine.basic.game.input;

import johnengine.basic.game.components.CController;

public interface IControllable {

    public void control(AControllerAction action);
    
    public default void setController(CController controller) {
        controller.setTarget(IControllable.this);
    }
}
