package johnengine.basic.game.components;

import java.util.ArrayList;
import java.util.List;

import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.ControlSchema;
import johnengine.basic.game.input.IControllable;
import johnengine.core.ITickable;
import johnengine.core.input.AInputEvent;
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
        List<AControllerAction> actions = new ArrayList<>();
        for( AInputEvent<?> event : this.inputSource.getEvents() )
        {
            AControllerAction action = this.controlSchema.getAction(event);
            
            if( action != null )
            actions.add(action);
        }
        
        for( AControllerAction action : actions )
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
