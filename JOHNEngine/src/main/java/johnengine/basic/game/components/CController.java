package johnengine.basic.game.components;

import java.util.Queue;

import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.ControlSchema;
import johnengine.basic.game.input.IControllable;
import johnengine.core.ITickable;
import johnengine.core.input.IInput;

public class CController implements ITickable {

    private IInput inputSource;
    private ControlSchema controlSchema;
    private IControllable controlledInstance;
    
    public CController(IInput inputSource, IControllable controlledInstance) {
        this.inputSource = inputSource;
        this.controlledInstance = controlledInstance;
        this.controlSchema = null;
    }
    
    public CController() {
        this(null, null);
    }

    
    @Override
    public void tick(float deltaTime) {
        IInput.State state = this.inputSource.getState();
        Queue<AControllerAction> actionQueue = this.controlSchema.generateActions(state);
        
        AControllerAction action;
        while( (action = actionQueue.poll()) != null )
        action.perform(this.controlledInstance);
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
