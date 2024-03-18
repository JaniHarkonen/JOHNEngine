package johnengine.basic.game.input.actions;

import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.Action;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public abstract class AMove extends AControllerAction {
    
    public static final float DEFAULT_INTENSITY = 1.0f;

    public float intensity;
    
    public AMove(Action action) {
        super(action);
        this.intensity = DEFAULT_INTENSITY;
    }
    
    
    protected void setIntensityFromInputEvent(AInputEvent<?> event, IInputConverter<?> converter) {
        this.intensity = (float) converter.convert(event);
    }
}
