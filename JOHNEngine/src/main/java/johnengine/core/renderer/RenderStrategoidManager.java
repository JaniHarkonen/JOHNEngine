package johnengine.core.renderer;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IRenderable;

public class RenderStrategoidManager {

    private Map<Class<?>, IRenderBufferStrategoid<? extends IRenderable>> strategoidMap;
    
    public RenderStrategoidManager() {
        this.strategoidMap = new HashMap<>();
    }
    
    
    public void addStrategoid(Class<?> clazz, IRenderBufferStrategoid<? extends IRenderable> strategoid) {
        this.strategoidMap.put(clazz, strategoid);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends IRenderable> IRenderBufferStrategoid<T> getStrategoid(Class<?> clazz) {
        return (IRenderBufferStrategoid<T>) this.strategoidMap.get(clazz);
    }
}
