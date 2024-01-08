package johnengine.basic.game.rewrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import johnengine.core.input.IInput;
import johnengine.core.input.IInputEvent;

public class ControlSchema {
        
    public static class ConverterEventPair {
        public IInputConverter<?, ?> converter;
        public IInputEvent<?> inputEvent;
        
        public ConverterEventPair(IInputConverter<?, ?> converter, IInputEvent<?> inputEvent) {
            this.converter = converter;
            this.inputEvent = inputEvent;
        }
    }
    
    
    private final Map<AControllerAction, ConverterEventPair> bindings;
    
    public ControlSchema() {
        this.bindings = new HashMap<>();
    }

    
    public List<Map.Entry<AControllerAction, ConverterEventPair>> generateActions(IInput.State targetState) {
        List<Map.Entry<AControllerAction, ConverterEventPair>> generatedActions = new ArrayList<>();
        for( Map.Entry<AControllerAction, ConverterEventPair> en : this.bindings.entrySet() )
        {
            if( en.getValue().inputEvent.check(targetState) )
            generatedActions.add(en);
        }
        
        return generatedActions;
    }
    
    
    public void addBinding(AControllerAction action, IInputConverter<?, ?> converter, IInputEvent<?> inputEvent) {
        this.bindings.put(action, new ConverterEventPair(converter, inputEvent));
    }
    
    /*protected Combo buildComboFromString(String bindingString) {
        List<IInputEvent> comboInputEvents = new ArrayList<>();
        
        
        return combo;
    }*/
}
