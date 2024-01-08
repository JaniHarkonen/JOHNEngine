package johnengine.basic.game.rewrite.actions;

import johnengine.basic.game.rewrite.AControllerAction;

public abstract class AMove extends AControllerAction {
    
    public static final float DEFAULT_INTENSITY = 1.0f;

    public float intensity;
    
    public AMove() {
        this.intensity = DEFAULT_INTENSITY;
    }
}
