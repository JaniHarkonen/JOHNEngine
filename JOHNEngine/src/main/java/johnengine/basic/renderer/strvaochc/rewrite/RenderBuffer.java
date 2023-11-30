package johnengine.basic.renderer.strvaochc.rewrite;

import java.util.ArrayList;
import java.util.List;

public class RenderBuffer {
    private List<RenderUnit> buffer;
    
    public RenderBuffer() {
        this.buffer = new ArrayList<RenderUnit>();
    }
    
    
    public void addUnit(RenderUnit unit) {
        this.buffer.add(unit);
    }
    
    
    public List<RenderUnit> getBuffer() {
        return this.buffer;
    }
}
