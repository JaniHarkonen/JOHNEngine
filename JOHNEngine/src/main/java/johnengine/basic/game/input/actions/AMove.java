package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.AControllerAction;
import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public abstract class AMove extends AControllerAction {
    
    public static final float DEFAULT_INTENSITY = 1.0f;

    public float intensity;
    
    public AMove() {
        this.intensity = DEFAULT_INTENSITY;
    }
    
    
    protected void setIntensityFromInputEvent(IInput.Event<?> event, IInputConverter<?> converter) {
        this.intensity = (float) converter.convert(event);
    }
}
