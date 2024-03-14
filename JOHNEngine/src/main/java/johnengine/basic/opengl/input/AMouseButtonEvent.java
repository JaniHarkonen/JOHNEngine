package johnengine.basic.opengl.input;

import johnengine.core.input.IInput;

abstract class AMouseButtonEvent implements IInput.Event<Boolean> {

    protected int mouseButton;
    protected boolean didOccur;
    
    public AMouseButtonEvent(int mouseButton) {
        this.didOccur = false;
        this.mouseButton = mouseButton;
    }
}
