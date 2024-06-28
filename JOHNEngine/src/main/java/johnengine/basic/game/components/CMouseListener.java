package johnengine.basic.game.components;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import johnengine.basic.game.gui.AGUIComponent;
import johnengine.core.ITickable;
import johnengine.core.input.AInputEvent;
import johnengine.core.input.IInput;

public class CMouseListener implements ITickable {
    
    /******************** EventContext-class ********************/
    
    public class EventContext {
        public String type;
        public AInputEvent<?> event;
        public AGUIComponent source;
        
        public EventContext(
            String type, AInputEvent<?> event, AGUIComponent source
        ) {
            this.type = type;
            this.event = event;
            this.source = source;
        }
    }
    
    
    /******************** CMouseListener-class ********************/
    
    public static final String EMIT_MOUSE_ENTER = "mouse-enter";
    public static final String EMIT_MOUSE_LEAVE = "mouse-leave";
    public static final String EMIT_MOUSE_PRESSED = "mouse-pressed";
    public static final String EMIT_MOUSE_DOWN = "mouse-down";
    public static final String EMIT_MOUSE_RELEASED = "mouse-released";
    
    private final AInputEvent<Point2D.Double> mouseMoveEvent;
    private IInput inputSource;
    private Map<AInputEvent<?>, String> expectedEvents;
    private AGUIComponent guiComponent;
    private float mouseX;
    private float mouseY;
    private boolean hasMouseEntered;
    
    public CMouseListener(
        IInput inputSource, 
        AInputEvent<Point2D.Double> mouseMoveEvent,
        AGUIComponent guiComponent
    ) {
        this.inputSource = inputSource;
        this.mouseMoveEvent = mouseMoveEvent;
        this.expectedEvents = new HashMap<>();
        this.guiComponent = guiComponent;
        this.hasMouseEntered = false;
    }
    
    public CMouseListener() {
        this(null, null, null);
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
    public void tick(float deltaTime) {
        if( this.inputSource == null )
        return;
        
        for( AInputEvent<?> event : this.inputSource.getEvents() )
        {
            String eventType = this.expectedEvents.get(event);
            
            if( event.equals(this.mouseMoveEvent) )
            {
                Point2D.Double mousePosition = 
                    ((AInputEvent<Point2D.Double>) event).getValue();
                this.mouseX = (float) mousePosition.x;
                this.mouseY = (float) mousePosition.y;
                
                boolean didMouseEnter = this.isMouseInsideComponent();
                
                if( !(didMouseEnter ^ this.hasMouseEntered) )
                continue;
                
                eventType = (
                    didMouseEnter ? 
                    CMouseListener.EMIT_MOUSE_ENTER : 
                    CMouseListener.EMIT_MOUSE_LEAVE
                );
                this.hasMouseEntered = didMouseEnter;
                this.eventOccurred(
                    new EventContext(eventType, event, this.guiComponent)
                );
            }
            else if( eventType != null && this.hasMouseEntered )
            {
                this.eventOccurred(
                    new EventContext(eventType, event, this.guiComponent)
                );
            }
        }
    }
    
    protected boolean isMouseInsideComponent() {
        float componentX = this.guiComponent.getX();
        float componentY = this.guiComponent.getY();
        
        return (
            this.mouseX >= componentX &&
            this.mouseY >= componentY &&
            this.mouseX < componentX + this.guiComponent.getWidth() &&
            this.mouseY < componentY + this.guiComponent.getHeight()
        );
    }
    
    public void listen(
        AInputEvent<?> listenedEvent, String emittedEvent
    ) {
        this.expectedEvents.put(listenedEvent, emittedEvent);
    }

    public void eventOccurred(EventContext context) {
        
    }
}
