package johnengine.core.renderer;

import java.util.HashMap;
import java.util.Map;

public abstract class ARenderBufferStrategy implements IRenderBufferStrategy, IDrawable {
    
    protected final Map<Class<?>, ARenderBufferStrategoid<?, ? extends ARenderBufferStrategy>> strategoidMap;
    protected ARenderer renderer; // to be removed
    
    protected ARenderBufferStrategy() {
        this.strategoidMap = new HashMap<Class<?>, ARenderBufferStrategoid<?, ? extends ARenderBufferStrategy>>();
    }
    
    
    protected void addStrategoid(Class<?> clazz, ARenderBufferStrategoid<?, ? extends ARenderBufferStrategy> renderBufferStrategoid) {
        this.strategoidMap.put(clazz, renderBufferStrategoid);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends IDrawable> ARenderBufferStrategoid<T, ? extends ARenderBufferStrategy> getStrategoid(T instance) {
        return (ARenderBufferStrategoid<T, ? extends ARenderBufferStrategy>) this.strategoidMap.get(instance.getClass()).newInstance();
    }
    
        // to be removed
    public void setRenderer(ARenderer renderer) {
        this.renderer = renderer;
    }
}
