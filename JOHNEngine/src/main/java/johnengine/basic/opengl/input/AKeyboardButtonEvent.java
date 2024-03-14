package johnengine.basic.opengl.input;

import johnengine.core.input.IInput;

abstract class AKeyboardButtonEvent implements IInput.Event<Boolean> {

    protected int key;
    protected boolean didOccur;
    
    public AKeyboardButtonEvent(int key) {
        this.didOccur = false;
        this.key = key;
    }
}
