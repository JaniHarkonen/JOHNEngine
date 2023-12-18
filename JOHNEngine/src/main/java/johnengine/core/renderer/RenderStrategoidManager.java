package johnengine.core.renderer;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IRenderable;

public class RenderStrategoidManager {

    private Map<Class<?>, IRenderStrategoid<? extends IRenderable>> strategoidMap;
    
    public RenderStrategoidManager() {
        this.strategoidMap = new HashMap<>();
    }
    
    
    public void addStrategoid(Class<?> clazz, IRenderStrategoid<? extends IRenderable> strategoid) {
        this.strategoidMap.put(clazz, strategoid);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends IRenderable> IRenderStrategoid<T> getStrategoid(Class<?> clazz) {
        return (IRenderStrategoid<T>) this.strategoidMap.get(clazz);
    }
}
