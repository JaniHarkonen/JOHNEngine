package johnengine.basic.game.input.cvrters;

import java.awt.geom.Point2D;

import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class MouseKeyboardPointConverter implements IInputConverter<Point2D.Float>{
    
    private IInput.Event<Point2D.Double> inputEvent;
    
    @Override
    public Point2D.Float convert() {
        Point2D.Double pointD = this.inputEvent.getValue();
        return new Point2D.Float((float) pointD.x, (float) pointD.y);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setEvent(IInput.Event<?> event) {
        this.inputEvent = (IInput.Event<Point2D.Double>) event;
    }
}
