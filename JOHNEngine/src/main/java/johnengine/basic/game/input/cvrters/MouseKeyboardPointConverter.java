package johnengine.basic.game.input.cvrters;

import java.awt.geom.Point2D;

import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class MouseKeyboardPointConverter implements IInputConverter<Point2D.Double, Point2D.Float>{
    
    private AInputEvent<Point2D.Double> inputEvent;
    
    @Override
    public Point2D.Float convert() {
        Point2D.Double pointD = this.inputEvent.getValue();
        return new Point2D.Float((float) pointD.x, (float) pointD.y);
    }

    @Override
    public void setEvent(AInputEvent<Point2D.Double> event) {
        this.inputEvent = event;
    }
}
