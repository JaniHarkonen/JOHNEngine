package johnengine.core.renderer;

import java.util.HashMap;
import java.util.Map;

import johnengine.core.IRenderable;

public class SubmissionStrategyManager {

    private Map<
        Class<?>, 
        IRenderSubmissionStrategy<? extends IRenderable>
    > strategyMap;
    
    public SubmissionStrategyManager() {
        this.strategyMap = new HashMap<>();
    }
    
    
    public SubmissionStrategyManager addStrategy(
        Class<?> clazz, 
        IRenderSubmissionStrategy<? extends IRenderable> strategy
    ) {
        this.strategyMap.put(clazz, strategy);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends IRenderable> IRenderSubmissionStrategy<T> 
        getStrategy(Class<?> clazz) {
        return (IRenderSubmissionStrategy<T>) this.strategyMap.get(clazz);
    }
}
