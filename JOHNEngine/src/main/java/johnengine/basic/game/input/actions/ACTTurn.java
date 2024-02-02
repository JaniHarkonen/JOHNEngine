package johnengine.basic.game.input.actions;

import java.awt.geom.Point2D;

import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.Action;
import johnengine.basic.game.input.IControllable;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ACTTurn extends AControllerAction {
    
    public ACTTurn() {
        super(Action.TURN);
    }
    
    public float deltaX;
    public float deltaY;
    
    
    @Override
    public void perform(IControllable target) {
        //target.turn(this.deltaX, this.deltaY);
        target.control(this);
    }
    
    @Override
    public ACTTurn createInstance(IInput.Event<?> event, IInputConverter<?> converter) {
        ACTTurn action = new ACTTurn();
        Point2D.Float delta = (Point2D.Float) converter.convert(event);
        action.deltaX = delta.x;
        action.deltaY = delta.y;
        
        return action;
    }
}
