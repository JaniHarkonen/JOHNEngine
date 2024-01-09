package johnengine.basic.game.rewrite;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import johnengine.core.input.IInput;
import johnengine.core.input.IInputConverter;

public class ControlSchema {
        
    public static class ConverterEventPair {
        public final IInputConverter<?> converter;
        public final IInput.Event<?> inputEvent;
        
        public ConverterEventPair(IInputConverter<?> converter, IInput.Event<?> inputEvent) {
            this.converter = converter;
            this.inputEvent = inputEvent;
        }
    }
    
    
    private final Map<AControllerAction, ConverterEventPair> bindings;
    
    public ControlSchema() {
        this.bindings = new HashMap<>();
    }
    
        // Copy constructor
    /*public ControlSchema(ControlSchema source) {
        this();
        
        for(  )
    }*/

    
    public LinkedList<AControllerAction> generateActions(IInput.State targetState) {
        LinkedList<AControllerAction> generatedActions = new LinkedList<>();
        for( Map.Entry<AControllerAction, ConverterEventPair> en : this.bindings.entrySet() )
        {
            ConverterEventPair pair = en.getValue();
            IInput.Event<?> inputEvent = pair.inputEvent;
            
                // Check whether the input event was triggered
            if( !inputEvent.check(targetState) )
            continue;
            
                // Create an action based on the input event and pass on the 
                // associated converter which will be used to convert the value
                // of the input event into a format accepted by the action
            IInputConverter<?> converter = pair.converter;
            generatedActions.add(en.getKey().createInstance(inputEvent, converter));
        }
            
        return generatedActions;
    }
    
    public ControlSchema addBinding(AControllerAction action, IInputConverter<?> converter, IInput.Event<?> inputEvent) {
        this.bindings.put(action, new ConverterEventPair(converter, inputEvent));
        return this;
    }
    
    /*protected Combo buildComboFromString(String bindingString) {
        List<IInputEvent> comboInputEvents = new ArrayList<>();
        
        
        return combo;
    }*/
}
