package johnengine.basic.game.input;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInputConverter;

public class ControlSchema {
    private static class ConverterActionPair {
        private IInputConverter<?> converter;
        private AControllerAction action;
        
        private ConverterActionPair(
            IInputConverter<?> converter, 
            AControllerAction action
        ) {
            this.converter = converter;
            this.action = action;
        }
    }
    
    
    private final Map<AInputEvent<?>, ConverterActionPair> bindings;
    
    public ControlSchema() {
        this.bindings = new HashMap<>();
    }
    
    
    public ControlSchema bind(
        AInputEvent<?> event,
        AControllerAction action, 
        IInputConverter<?> converter
    ) {
        this.bindings.put(event, new ConverterActionPair(converter, action));
        return this;
    }
    
    public ControlSchema unbind(AInputEvent<?> event) {
        this.bindings.remove(event);
        return this;
    }
    
    public AControllerAction getAction(AInputEvent<?> event) {
        ConverterActionPair pair = this.bindings.get(event);    
        
        if( pair == null )
        return null;
        
        return pair.action.createInstance(event, pair.converter);
    }
}
