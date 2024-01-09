package johnengine.basic.window;

import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class MouseKeyboardBooleanConverter implements IInputConverter<Float>{
    
    private IInput.Event<Boolean> inputEvent;
    
    @Override
    public Float convert() {
        return (this.inputEvent.getValue() ? 1.0f : 0.0f);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setEvent(IInput.Event<?> event) {
        this.inputEvent = (IInput.Event<Boolean>) event;
    }
}
