package johnengine.basic.game.input.cvrters;

import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class MouseKeyboardBooleanConverter implements IInputConverter<Float>{
    
    private AInputEvent<Boolean> inputEvent;
    
    @Override
    public Float convert() {
        return (this.inputEvent.getValue() ? 1.0f : 0.0f);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setEvent(AInputEvent<?> event) {
        this.inputEvent = (AInputEvent<Boolean>) event;
    }
}
