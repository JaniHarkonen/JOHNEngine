package johnengine.basic.game.rewrite;

import java.util.Map;

import johnengine.core.ITickable;
import johnengine.core.input.IInput;

public class CController implements ITickable {

    private IInput inputSource;
    private ControlSchema controlSchema;
    private IControllable controlledInstance;
    
    public CController() {
        this.inputSource = null;
        this.controlSchema = null;
        this.controlledInstance = null;
    }

    
    @Override
    public void tick(float deltaTime) {
        IInput.State state = this.inputSource.getState();
        for( Map.Entry<AControllerAction, ControlSchema.ConverterEventPair> entry : this.controlSchema.generateActions(state) )
        entry.getKey().perform(this.controlledInstance, entry.getValue().inputEvent, entry.getValue().converter);
    }
    
    
    public void setSchema(ControlSchema schema) {
        this.controlSchema = schema;
    }
    
    public void setTarget(IControllable target) {
        this.controlledInstance = target;
    }
    
    public void setSource(IInput inputSource) {
        this.inputSource = inputSource;
    }
}
