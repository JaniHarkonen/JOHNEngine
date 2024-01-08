package johnengine.basic.game.rewrite;

import johnengine.core.input.IInputEvent;

//public class MouseKeyboardBooleanConverter extends AInputConverter<Boolean, Float> {
public class MouseKeyboardBooleanConverter implements IInputConverter<Boolean, Float> {
    
    private IInputEvent<Boolean> inputEvent;

    public MouseKeyboardBooleanConverter(IInputEvent<Boolean> inputEvent) {
        this.inputEvent = inputEvent;
    }
    /*@Override
    public Float convert(IInputEvent<?> inputEvent) {
        return (((IInputEvent<Boolean>) inputEvent).getValue() ? 1.0f : 0.0f);
    }*/
    
    @Override
    public Float convert() {
        return (this.inputEvent.getValue() ? 1.0f : 0.0f);
    }

    @Override
    public void setEvent(IInputEvent<Boolean> event) {
        this.inputEvent = event;
    }
}
