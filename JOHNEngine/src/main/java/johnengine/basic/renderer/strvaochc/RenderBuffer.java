package johnengine.basic.renderer.strvaochc;

import java.util.ArrayList;
import java.util.List;

public class RenderBuffer {
    private List<RenderUnit> buffer;
    
    public RenderBuffer() {
        this.buffer = new ArrayList<>();
    }
    
    
    public void addUnit(RenderUnit unit) {
        this.buffer.add(unit);
    }
    
    
    public List<RenderUnit> getBuffer() {
        return this.buffer;
    }
}
