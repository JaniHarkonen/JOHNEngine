package johnengine.basic.game.rewrite.actions;

import java.awt.geom.Point2D;

import johnengine.basic.game.rewrite.AControllerAction;
import johnengine.basic.game.rewrite.IControllable;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ACTTurn extends AControllerAction {
    
    public float deltaX;
    public float deltaY;
    
    
    @Override
    public void perform(IControllable target) {
        target.turn(this.deltaX, this.deltaY);
    }
    
    @Override
    public ACTTurn createInstance(IInput.Event<?> event, IInputConverter<?> converter) {
        ACTTurn action = new ACTTurn();
        Point2D.Float mouseDelta = (Point2D.Float) converter.convert(event);
        action.deltaX = mouseDelta.x;
        action.deltaY = mouseDelta.y;
        
        return action;
    }
}
