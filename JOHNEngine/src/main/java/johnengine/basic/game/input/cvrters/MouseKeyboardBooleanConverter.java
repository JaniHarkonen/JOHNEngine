package johnengine.basic.game.input.cvrters;

import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class MouseKeyboardBooleanConverter implements IInputConverter<Boolean, Float>{
    
    private AInputEvent<Boolean> inputEvent;
    
    @Override
    public Float convert() {
        return (this.inputEvent.getValue() ? 1.0f : 0.0f);
    }

    @Override
    public void setEvent(AInputEvent<Boolean> event) {
        this.inputEvent = event;
    }
}
