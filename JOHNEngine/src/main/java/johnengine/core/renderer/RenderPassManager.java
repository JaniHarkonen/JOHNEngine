package johnengine.core.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderPassManager {

    private Map<String, IRenderPass> passes;
    private List<String> order;
    
    public RenderPassManager() {
        this.passes = new HashMap<>();
        this.order = new ArrayList<>();
    }
    
    
    public void addRenderPass(String passKey, IRenderPass renderPass) {
        this.passes.put(passKey, renderPass);
        this.order.add(passKey);
    }
    
    public void removeRenderPass(String passKey) {
        if( this.passes.remove(passKey) == null )
        return;
        
        for( int i = 0; i < this.order.size(); i++ )
        {
            if( !this.order.get(i).equals(passKey) )
            continue;
            
            this.order.remove(i);
            return;
        }
    }
    
    public List<String> getOrder() {
        return this.order;
    }
    
    public IRenderPass getPass(String passKey) {
        return this.passes.get(passKey);
    }
}
